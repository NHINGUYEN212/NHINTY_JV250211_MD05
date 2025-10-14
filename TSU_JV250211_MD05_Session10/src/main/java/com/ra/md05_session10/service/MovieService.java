package com.ra.md05_session10.service;

import com.ra.md05_session10.model.dto.request.MovieDTO;
import com.ra.md05_session10.model.dto.response.DataResponse;
import com.ra.md05_session10.model.dto.response.PaginationResponse;
import com.ra.md05_session10.model.entity.Movie;
import com.ra.md05_session10.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class MovieService {
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private CloudinaryService cloudinaryService;

    public ResponseEntity<DataResponse<PaginationResponse<Movie>>> getAllMovies(Pageable pageable) {
        Page<Movie> page = movieRepository.findAll(pageable);
        PaginationResponse<Movie> paginationResponse = new PaginationResponse<>();
        paginationResponse.setContent(page.getContent());
        paginationResponse.setCurrentPage(page.getNumber() + 1);
        paginationResponse.setTotalPages(page.getTotalPages());
        paginationResponse.setTotalElements(page.getTotalElements());
        paginationResponse.setSize(page.getSize());
        DataResponse<PaginationResponse<Movie>> dataResponse = new DataResponse<>();
        dataResponse.setData(paginationResponse);
        dataResponse.setMessage("Successfully retrieved data");
        dataResponse.setStatus(200);
        return new ResponseEntity<>(dataResponse, HttpStatus.OK);
    }

    public Movie getMovieById(Long id) {
        return movieRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Movie not found"));
    }

    public ResponseEntity<DataResponse<?>> addMovie(MovieDTO movieDTO) {
        if (movieDTO.getPoster().isEmpty() || movieDTO.getPoster().getSize() == 0) {
            DataResponse<?> dataResponse = DataResponse
                    .builder()
                    .message("poster cannot be empty")
                    .status(400)
                    .build();
            return new ResponseEntity<>(dataResponse, HttpStatus.BAD_REQUEST);
        }
        DataResponse<Movie> dataResponse = new DataResponse<>();
        Movie movie = convertMovieDTOToMovie(movieDTO);
        movie.setPoster(cloudinaryService.upload(movieDTO.getPoster()));
        try {

            dataResponse.setData(movieRepository.save(movie));
            dataResponse.setMessage("Successfully added movie");
            dataResponse.setStatus(201);
            return new ResponseEntity<>(dataResponse, HttpStatus.CREATED);
        }catch (Exception e) {
            dataResponse.setMessage(e.getMessage());
            dataResponse.setStatus(400);
            return new ResponseEntity<>(dataResponse, HttpStatus.BAD_REQUEST);
        }

    }

    public ResponseEntity<DataResponse<?>> updateMovie(Long id, MovieDTO movieDTO) {
        DataResponse<Movie> dataResponse = new DataResponse<>();
        Movie movie = getMovieById(id);
        if(movie != null) {
            Movie updatedMovie = convertMovieDTOToMovie(movieDTO);
            updatedMovie.setId(id);
            if(!movieDTO.getPoster().isEmpty() || movieDTO.getPoster().getSize() > 0) {
                updatedMovie.setPoster(cloudinaryService.upload(movieDTO.getPoster()));
            }else {
                updatedMovie.setPoster(movie.getPoster());
            }
            try {
                dataResponse.setData(movieRepository.save(updatedMovie));
                dataResponse.setMessage("Successfully updated movie");
                dataResponse.setStatus(200);
                return new ResponseEntity<>(dataResponse, HttpStatus.OK);
            }catch (Exception e) {
                dataResponse.setMessage(e.getMessage());
                dataResponse.setStatus(400);
                return new ResponseEntity<>(dataResponse, HttpStatus.BAD_REQUEST);
            }
        }else {
            dataResponse.setMessage("Movie not found");
            dataResponse.setStatus(404);
            return new ResponseEntity<>(dataResponse, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<DataResponse<String>> deleteMovie(Long id) {
        DataResponse<String> dataResponse = new DataResponse<>();
        Movie movie = getMovieById(id);
        if(movie != null) {
            try {
                movieRepository.delete(movie);
                dataResponse.setMessage("Successfully deleted movie");
                dataResponse.setStatus(200);
                return new ResponseEntity<>(dataResponse, HttpStatus.OK);
            }catch (Exception e) {
                dataResponse.setMessage(e.getMessage());
                dataResponse.setStatus(400);
                return new ResponseEntity<>(dataResponse, HttpStatus.BAD_REQUEST);
            }
        }else {
            dataResponse.setMessage("Movie not found");
            dataResponse.setStatus(404);
            return new ResponseEntity<>(dataResponse, HttpStatus.NOT_FOUND);
        }
    }

    public Movie convertMovieDTOToMovie(MovieDTO movieDTO) {
        return Movie
                .builder()
                .title(movieDTO.getTitle())
                .duration(movieDTO.getDuration())
                .releaseDate(movieDTO.getReleaseDate())
                .genre(movieDTO.getGenre())
                .build();
    }
}
