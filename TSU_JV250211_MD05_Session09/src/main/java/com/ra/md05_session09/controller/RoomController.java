package com.ra.md05_session09.controller;

import com.ra.md05_session09.model.dto.request.RoomDTO;
import com.ra.md05_session09.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/admin/rooms")
@PreAuthorize("hasRole('ADMIN')")
public class RoomController {
    @Autowired
    private RoomService roomService;

    @GetMapping
    public ResponseEntity<Page<RoomDTO>> getAllRooms(
            @PageableDefault(size = 10, page = 0) Pageable pageable) {
        return ResponseEntity.ok(roomService.getAllRooms(pageable));
    }

    @PostMapping
    public ResponseEntity<RoomDTO> addRoom(@RequestBody RoomDTO roomDTO) {
        RoomDTO newRoom = roomService.addRoom(roomDTO);
        return new ResponseEntity<>(newRoom, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoomDTO> updateRoom(@PathVariable Long id, @RequestBody RoomDTO roomDTO) {
        RoomDTO updatedRoom = roomService.updateRoom(roomDTO, id);
        return new ResponseEntity<>(updatedRoom, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
        roomService.deleteRoomById(id);
        return ResponseEntity.noContent().build();
    }
}