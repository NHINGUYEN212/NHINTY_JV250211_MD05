package ra.com.service;

import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.com.exception.ResourceNotFoundException;
import ra.com.model.dto.ShowtimeDTO;
import ra.com.model.dto.ShowtimeResponseDTO;
import ra.com.model.entity.Showtime;
import ra.com.repository.MovieRepository;
import ra.com.repository.ScreenRoomRepository;
import ra.com.repository.ShowtimeRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ShowtimeService {
    @Autowired
    private ShowtimeRepository showtimeRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private ScreenRoomRepository screenRoomRepository;

    public List<Showtime> getAllShowTime() {
        return showtimeRepository.findAll();
    }

    public Showtime getShowTimeById(Long id) {
        return showtimeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Show time not found with id: " + id));
    }

    public Showtime addShowTime(ShowtimeDTO showtimeDTO) {
        return showtimeRepository.save(convertShowTimeDTOToShowTime(showtimeDTO));
    }

    public Showtime updateShowTime(ShowtimeDTO showtimeDTO, Long id) {
        Showtime showtime = getShowTimeById(id);
        showtime.setShowtime(showtimeDTO.getShowtime());
        showtime.setMovie(movieRepository.getReferenceById(showtimeDTO.getMovieId()));
        showtime.setScreenRoom(screenRoomRepository.getReferenceById(showtimeDTO.getScreenRoomId()));
        return showtimeRepository.save(showtime);
    }

    public void deleteShowTime(Long id) {
        Showtime showtime = getShowTimeById(id);
        showtimeRepository.delete(showtime);
    }

    public Showtime convertShowTimeDTOToShowTime(ShowtimeDTO showtimeDTO) {
        return Showtime
                .builder()
                .movie(movieRepository.getReferenceById(showtimeDTO.getMovieId()))
                .screenRoom(screenRoomRepository.getReferenceById(showtimeDTO.getScreenRoomId()))
                .showtime(showtimeDTO.getShowtime())
                .build();
    }

    public ShowtimeResponseDTO convertEntityToResponseDTO(Showtime showtime) {
        return ShowtimeResponseDTO.builder()
                .id(showtime.getId())
                .movieId(showtime.getMovie() != null ? showtime.getMovie().getId() : null)
                .screenRoomId(showtime.getScreenRoom() != null ? showtime.getScreenRoom().getId() : null)
                .showtime(showtime.getShowtime())
                .build();
    }
}
