package ra.com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.com.exception.ResourceNotFoundException;
import ra.com.model.dto.MovieDTO;
import ra.com.model.entity.Movie;
import ra.com.repository.MovieRepository;

import java.util.List;

@Service
public class MovieService {
    @Autowired
    private MovieRepository movieRepository;

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Movie getMovieById(Long id) {
        return movieRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Movie not found with id: " + id));
    }

    public Movie addMovie(MovieDTO movieDTO) {
        return movieRepository.save(convertMovieDTOToMovie(movieDTO));
    }

    public Movie convertMovieDTOToMovie(MovieDTO movieDTO) {
        return Movie
                .builder()
                .description(movieDTO.getDescription())
                .title(movieDTO.getTitle())
                .duration(movieDTO.getDuration())
                .build();
    }

    public Movie updateMovie(MovieDTO movieDTO, Long id) {
        Movie movie = getMovieById(id);
        movie.setTitle(movieDTO.getTitle());
        movie.setDescription(movieDTO.getDescription());
        movie.setDuration(movieDTO.getDuration());
        return movieRepository.save(movie);
    }

    public void deleteMovie(Long id) {
        Movie movie = getMovieById(id);
        movieRepository.delete(movie);
    }




}
