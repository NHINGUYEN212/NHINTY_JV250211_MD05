package com.ra.md05_session09.service;

import com.ra.md05_session09.model.dto.request.RoomDTO;
import com.ra.md05_session09.model.entity.Room;
import com.ra.md05_session09.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class RoomService {
    @Autowired
    private RoomRepository roomRepository;


    public Page<RoomDTO> getAllRooms(Pageable pageable) {
        Page<Room> roomPage = roomRepository.findAll(pageable);
        return roomPage.map(this::convertEntityToDTO);
    }

    private RoomDTO convertEntityToDTO(Room room) {
        return new RoomDTO(room.getRoomName(), room.getType(), room.getPrice());
    }

    public void save(Room room) {
        roomRepository.save(room);
    }

    public RoomDTO addRoom(RoomDTO roomDTO) {
        Room newRoom = Room.builder()
                .roomName(roomDTO.getRoomName())
                .type(roomDTO.getType())
                .price(roomDTO.getPrice())
                .build();
        Room savedRoom = roomRepository.save(newRoom);
        return convertEntityToDTO(savedRoom);
    }

    public Room findRoomById(Long id) {
        return roomRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Room Not Found"));
    }

    public RoomDTO updateRoom(RoomDTO roomDTO, Long id) {
        Room updateRoom = findRoomById(id);
        updateRoom.setRoomName(roomDTO.getRoomName());
        updateRoom.setType(roomDTO.getType());
        updateRoom.setPrice(roomDTO.getPrice());
        Room savedRoom = roomRepository.save(updateRoom);
        return convertEntityToDTO(savedRoom);

    }

    public void deleteRoomById(Long id) {
        Room deleteRoom = findRoomById(id);
        if (!deleteRoom.getReservations().isEmpty()) {
            throw new IllegalStateException("Cannot delete a room that has active reservations.");
        }
        roomRepository.delete(deleteRoom);
    }
}
