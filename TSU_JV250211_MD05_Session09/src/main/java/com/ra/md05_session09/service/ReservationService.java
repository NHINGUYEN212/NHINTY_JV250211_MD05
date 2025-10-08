package com.ra.md05_session09.service;

import com.ra.md05_session09.model.constant.Status;
import com.ra.md05_session09.model.dto.response.ReservationResponse;
import com.ra.md05_session09.model.entity.Customer;
import com.ra.md05_session09.model.entity.Reservation;
import com.ra.md05_session09.model.entity.Room;
import com.ra.md05_session09.repository.ReservationRepository;
import com.ra.md05_session09.repository.RoomRepository;
import com.ra.md05_session09.security.CustomerPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private RoomRepository roomRepository;

    @Transactional
    public ReservationResponse bookRoom(Long roomId, CustomerPrincipal customerPrincipal) {
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new NoSuchElementException("Room Not Found"));
        boolean isBooked = reservationRepository.existsByRoomsAndStatusIn(room, List.of(Status.PENDING, Status.CONFIRMED));
        if (isBooked) {
            throw new IllegalStateException("Room with ID " + roomId + " is already booked or pending confirmation.");
        }
        Reservation reservation = new Reservation();
        reservation.setCustomer(customerPrincipal.customer);
        reservation.getRooms().add(room);
        reservation.setStatus(Status.PENDING);
        Reservation newReservation = reservationRepository.save(reservation);
        return convertToReservationResponse(newReservation);
    }

    private ReservationResponse convertToReservationResponse(Reservation reservation) {
        List<String> roomNames = reservation.getRooms().stream().map(Room::getRoomName).collect(Collectors.toList());
        return ReservationResponse.builder()
                .id(reservation.getId())
                .customerName(reservation.getCustomer().getName())
                .roomNames(roomNames)
                .status(reservation.getStatus())
                .build();
    }

    @Transactional
    public void cancelReservation(CustomerPrincipal customerPrincipal, Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Reservation Not Found"));
        Customer currentUser = customerPrincipal.getCustomer();
        boolean isAdmin = currentUser.getRole().equals("ADMIN");
        boolean isOwner = reservation.getCustomer().getId().equals(currentUser.getId());
        if (!isAdmin && !isOwner) {
            throw new AccessDeniedException("You are not allowed to perform this action.");
        }
        if (reservation.getStatus() != Status.PENDING) {
            throw new IllegalStateException("Can't cancel reservation with status " + reservation.getStatus());
        }
        reservationRepository.delete(reservation);
    }

    public Page<ReservationResponse> getAllReservations(Pageable pageable) {
        Page<Reservation> reservationPage = reservationRepository.findAll(pageable);
        return reservationPage.map(reservation -> convertToReservationResponse(reservation));
    }

    @Transactional
    public ReservationResponse confirmReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Reservation Not Found"));
        if (reservation.getStatus() != Status.PENDING) {
            throw new IllegalStateException("Can't confirm reservation with status " + reservation.getStatus());
        }
        reservation.setStatus(Status.CONFIRMED);
        Reservation updatedReservation = reservationRepository.save(reservation);
        return convertToReservationResponse(updatedReservation);
    }

}
