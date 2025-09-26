package ra.com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.com.model.dto.CinemaDTO;
import ra.com.model.entity.Cinema;
import ra.com.service.CinemaService;

import java.util.List;

@RestController
@RequestMapping("/cinemas")
public class CinemaController {
    @Autowired
    private CinemaService cinemaService;

    @GetMapping
    public ResponseEntity<List<Cinema>> getAllCinemas() {
        return new ResponseEntity<>(cinemaService.getAllCinemas(), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addCinema(@RequestBody CinemaDTO cinemaDTO) {
        Cinema cinema = cinemaService.addCinema(cinemaDTO);
        return new ResponseEntity<>(cinema, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editCinema(@RequestBody CinemaDTO cinemaDTO, @PathVariable Long id) {
        Cinema editCinema = cinemaService.updateCinema(cinemaDTO, id);
        return new ResponseEntity<>(editCinema, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCinema(@PathVariable Long id) {
        cinemaService.deleteCinemaById(id);
        return new ResponseEntity<>("Delete cinema successfully", HttpStatus.OK);
    }

}
