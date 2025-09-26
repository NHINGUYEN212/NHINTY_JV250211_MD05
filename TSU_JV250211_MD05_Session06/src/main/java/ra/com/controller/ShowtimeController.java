package ra.com.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.com.model.dto.ShowtimeDTO;
import ra.com.model.dto.ShowtimeResponseDTO;
import ra.com.model.entity.Showtime;
import ra.com.service.ScreenRoomService;
import ra.com.service.ShowtimeService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/showtimes")
public class ShowtimeController {
    @Autowired
    private ShowtimeService showtimeService;

    @GetMapping
    public ResponseEntity<List<ShowtimeResponseDTO>> getAllShowTimes() {
        List<Showtime> showTimes = showtimeService.getAllShowTime();
        List<ShowtimeResponseDTO> responseDTOS = showTimes.stream()
                .map(showtime -> showtimeService.convertEntityToResponseDTO(showtime)).collect(Collectors.toList());
        return new ResponseEntity<>(responseDTOS, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addShowTime(@RequestBody ShowtimeDTO showtimeDTO) {
        Showtime showtime = showtimeService.addShowTime(showtimeDTO);
        ShowtimeResponseDTO responseDTO = showtimeService.convertEntityToResponseDTO(showtime);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editShowTime(@RequestBody ShowtimeDTO showtimeDTO, @PathVariable Long id) {
        Showtime updateShowTime = showtimeService.updateShowTime(showtimeDTO, id);
        ShowtimeResponseDTO responseDTO = showtimeService.convertEntityToResponseDTO(updateShowTime);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteShowTime(@PathVariable Long id) {
        showtimeService.deleteShowTime(id);
        return new ResponseEntity<>("Delete showtime successfully", HttpStatus.OK);
    }
}
