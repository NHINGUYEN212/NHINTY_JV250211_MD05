package ra.tsu_jv250211_md05_session02.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ra.tsu_jv250211_md05_session02.model.dto.MovieDTO;
import ra.tsu_jv250211_md05_session02.model.entity.Movie;
import ra.tsu_jv250211_md05_session02.service.MovieService;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping
    public String listMovies(Model model, @RequestParam(name = "page",defaultValue = "0") int page ,
                             @RequestParam(name = "size" , defaultValue = "5") int size ,
                             @RequestParam(name = "search" , required = false) String search) {
        Page<Movie> moviePage = movieService.findAll(PageRequest.of(page, size ));
        List<Integer> pages = new ArrayList<>();
        for (int i = 1 ; i <= moviePage.getTotalPages(); i++) {
            pages.add(i);
        }
        model.addAttribute("movies", moviePage.getContent());
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("totalPage", pages);
        return "movieList";
    }

    @GetMapping("/add")
    public String addMovie(Model model) {
        model.addAttribute("movie", new MovieDTO());
        return "addMovie";
    }
    @PostMapping("/add")
    public String addMovie(@Valid @ModelAttribute("movie") MovieDTO movieDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes,
                           Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("movie", movieDTO);
            return "addMovie";
        }
        Movie movie = movieService.addMovie(movieDTO);
        if (movie != null) {
            redirectAttributes.addFlashAttribute("message", "Movie added successfully");
            return "redirect:/movies";
        }else {
            model.addAttribute("message", "Movie add failed");
            model.addAttribute("movie", movieDTO);
            return "addMovie";
        }

    }

    @GetMapping("/delete/{id}")
    public String deleteMovie(@PathVariable Long id , RedirectAttributes redirectAttributes) {
        boolean rs = movieService.deleteById(id);
        if (rs){
            redirectAttributes.addFlashAttribute("message", "Movie deleted successfully");
        }else {
            redirectAttributes.addFlashAttribute("message", "Movie not deleted successfully");
        }
        return "redirect:/movies";
    }



}

