package com.ra.md05_session09.controller;

import com.ra.md05_session09.model.dto.response.ReservationResponse;
import com.ra.md05_session09.model.entity.Customer;
import com.ra.md05_session09.security.CustomerPrincipal;
import com.ra.md05_session09.service.ReservationService;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    @Autowired
    private ReservationService reservationService;

    @PostMapping("/book/{roomId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ReservationResponse> bookRoom(
            @PathVariable Long roomId,
            @AuthenticationPrincipal CustomerPrincipal customerPrincipal) {
        ReservationResponse response = reservationService.bookRoom(roomId, customerPrincipal);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/cancel/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> cancelReservation(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomerPrincipal customerPrincipal) {
        reservationService.cancelReservation(customerPrincipal, id);
        return new ResponseEntity<>("Reservation cancelled successfully", HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<ReservationResponse>> getAllReservations(
            @PageableDefault(size = 10, page = 0, sort = "id") Pageable pageable) {

        Page<ReservationResponse> reservationPage = reservationService.getAllReservations(pageable);
        return new ResponseEntity<>(reservationPage, HttpStatus.OK);
    }

    @PutMapping("/confirm/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ReservationResponse> confirmReservation(@PathVariable Long id) {
        ReservationResponse response = reservationService.confirmReservation(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
