package ra.com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.com.exception.ResourceNotFoundException;
import ra.com.model.dto.CinemaDTO;
import ra.com.model.entity.Cinema;
import ra.com.repository.CinemaRepository;

import java.util.List;

@Service
public class CinemaService {
    @Autowired
    private CinemaRepository cinemaRepository;

    public List<Cinema> getAllCinemas() {
        return cinemaRepository.findAll();
    }

    public Cinema getById(Long id) {
        return cinemaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cinema not found with id: " + id));
    }

    public Cinema addCinema(CinemaDTO cinemaDTO) {
        return cinemaRepository.save(convertCinemaDTOToCinema(cinemaDTO));
    }

    public Cinema convertCinemaDTOToCinema(CinemaDTO cinemaDTO) {
        return Cinema
                .builder()
                .name(cinemaDTO.getName())
                .location(cinemaDTO.getLocation())
                .capacity(cinemaDTO.getCapacity())
                .build();
    }

    public Cinema updateCinema(CinemaDTO cinemaDTO, Long id) {
        Cinema cinema = getById(id);
        cinema.setName(cinemaDTO.getName());
        cinema.setLocation(cinemaDTO.getLocation());
        cinema.setCapacity(cinemaDTO.getCapacity());
        return cinemaRepository.save(cinema);
    }

    public void deleteCinemaById(Long id) {
        Cinema cinema = getById(id);
        cinemaRepository.deleteById(id);
    }




}
