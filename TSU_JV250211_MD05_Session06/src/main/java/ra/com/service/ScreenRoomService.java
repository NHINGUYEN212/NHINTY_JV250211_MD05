package ra.com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.com.exception.ResourceNotFoundException;
import ra.com.model.dto.ScreenRoomDTO;
import ra.com.model.dto.ScreenRoomResponseDTO;
import ra.com.model.entity.ScreenRoom;
import ra.com.repository.CinemaRepository;
import ra.com.repository.ScreenRoomRepository;

import java.util.List;

@Service
public class ScreenRoomService {
    @Autowired
    private ScreenRoomRepository screenRoomRepository;
    @Autowired
    private CinemaRepository cinemaRepository;

    public List<ScreenRoom> getAllScreenRoom() {
        return screenRoomRepository.findAll();
    }

    public ScreenRoom getScreenRoomById(Long id) {
        return screenRoomRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Screen room not found with id: " + id));
    }

    public ScreenRoom addScreenRoom(ScreenRoomDTO screenRoomDTO) {
        return screenRoomRepository.save(convertScreenRoomDTOToScreenRoom(screenRoomDTO));
    }

    public ScreenRoom convertScreenRoomDTOToScreenRoom (ScreenRoomDTO screenRoomDTO) {
        return ScreenRoom
                .builder()
                .name(screenRoomDTO.getName())
                .capacity(screenRoomDTO.getCapacity())
                .type(screenRoomDTO.getType())
                .cinema(cinemaRepository.getReferenceById(screenRoomDTO.getCinemaId()))
                .build();
    }

    public ScreenRoom updateScreenRoom(ScreenRoomDTO screenRoomDTO, Long id) {
        ScreenRoom screenRoom = getScreenRoomById(id);
        screenRoom.setName(screenRoomDTO.getName());
        screenRoom.setCapacity(screenRoomDTO.getCapacity());
        screenRoom.setType(screenRoomDTO.getType());
        screenRoom.setCinema(cinemaRepository.getReferenceById(screenRoomDTO.getCinemaId()));
        return screenRoomRepository.save(screenRoom);
    }

    public void deleteScreenRoom(Long id) {
        ScreenRoom screenRoom = getScreenRoomById(id);
        screenRoomRepository.delete(screenRoom);
    }

    public ScreenRoomResponseDTO convertEntityToResponseDTO(ScreenRoom screenRoom) {
        return new ScreenRoomResponseDTO(
                screenRoom.getId(),
                screenRoom.getName(),
                screenRoom.getCapacity(),
                screenRoom.getType(),
                screenRoom.getCinema() != null ? screenRoom.getCinema().getId() : null
        );
    }
}
