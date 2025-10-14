package com.ra.md05_session10.controller;

import com.ra.md05_session10.model.dto.request.MovieDTO;
import com.ra.md05_session10.model.dto.response.DataResponse;
import com.ra.md05_session10.model.dto.response.PaginationResponse;
import com.ra.md05_session10.model.entity.Movie;
import com.ra.md05_session10.service.MovieService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/manager/movies")
public class MovieController {
    @Autowired
    private MovieService movieService;

    @GetMapping
    public ResponseEntity<DataResponse<PaginationResponse<Movie>>> findAll(@RequestParam(name = "page",defaultValue = "0") int page ,
                                                                           @RequestParam(name = "size",defaultValue = "5") int size) {
        return movieService.getAllMovies(PageRequest.of(page,size));
    }

    @PostMapping("/add")
    public ResponseEntity<DataResponse<?>> saveMovie(@Valid @ModelAttribute MovieDTO movieDTO) {
        return movieService.addMovie(movieDTO);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<DataResponse<?>> updateMovie(@PathVariable long id, @Valid @ModelAttribute MovieDTO movieDTO) {
        return movieService.updateMovie(id,movieDTO);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<DataResponse<String>> deleteMovie(@PathVariable long id) {
        return movieService.deleteMovie(id);
    }
}