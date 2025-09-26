package ra.com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.com.model.dto.ScreenRoomDTO;
import ra.com.model.dto.ScreenRoomResponseDTO;
import ra.com.model.entity.ScreenRoom;
import ra.com.service.ScreenRoomService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/screenRooms")
public class ScreenRoomController {
    @Autowired
    private ScreenRoomService screenRoomService;

    @GetMapping
    public ResponseEntity<List<ScreenRoomResponseDTO>> getAllScreenRooms() {
        List<ScreenRoom> screenRooms = screenRoomService.getAllScreenRoom();
        List<ScreenRoomResponseDTO> responseDTOS = screenRooms.stream()
                .map(screenRoom -> screenRoomService.convertEntityToResponseDTO(screenRoom))
                .collect(Collectors.toList());
        return new ResponseEntity<>(responseDTOS, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<ScreenRoomResponseDTO> addScreenRoom(@RequestBody ScreenRoomDTO screenRoomDTO) {
        ScreenRoom screenRoom = screenRoomService.addScreenRoom(screenRoomDTO);
        ScreenRoomResponseDTO responseDTO = screenRoomService.convertEntityToResponseDTO(screenRoom);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ScreenRoomResponseDTO> editScreenRoom(@RequestBody ScreenRoomDTO screenRoomDTO, @PathVariable Long id) {
        ScreenRoom updatedScreenRoom = screenRoomService.updateScreenRoom(screenRoomDTO, id);
        ScreenRoomResponseDTO responseDTO = screenRoomService.convertEntityToResponseDTO(updatedScreenRoom);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteScreenRoom(@PathVariable Long id) {
        screenRoomService.deleteScreenRoom(id);
        return new ResponseEntity<>("Delete screen room successfully", HttpStatus.OK);
    }

}
