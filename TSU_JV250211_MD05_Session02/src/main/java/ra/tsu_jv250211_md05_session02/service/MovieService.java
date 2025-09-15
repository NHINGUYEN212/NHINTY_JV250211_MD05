package ra.tsu_jv250211_md05_session02.service;


//
//import com.example.session02.model.dto.MovieDTO;
//import com.example.session02.model.entity.Movie;
//import com.example.session02.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ra.tsu_jv250211_md05_session02.model.dto.MovieDTO;
import ra.tsu_jv250211_md05_session02.model.entity.Movie;
import ra.tsu_jv250211_md05_session02.repository.MovieRepository;

@Service
public class MovieService {
    @Autowired
    private MovieRepository movieRepository;

    public Page<Movie> findAll(Pageable pageable) {
        return movieRepository.findAll(pageable);
    }

    public boolean existsByTitle(String title) {
        long countTitle = movieRepository.countByTitle(title);
        return countTitle > 0;
    }

    public Movie addMovie(MovieDTO movieDTO) {
        Movie movie = convertMovieDTOToMovie(movieDTO);
        return movieRepository.save(movie);
    }

    public Movie updateMovie(MovieDTO movieDTO, long id) {
        Movie movie = convertMovieDTOToMovie(movieDTO);
        movie.setId(id);
        return movieRepository.save(movie);
    }

    public Movie findById(long id) {
        return movieRepository.findById(id).orElse(null);
    }

    public boolean deleteById(long id) {
        Movie movie = findById(id);
        if (movie != null) {
            movieRepository.delete(movie);
            return true;
        }else {
            return false;
        }
    }

    public Movie convertMovieDTOToMovie(MovieDTO movieDTO) {
        return Movie
                .builder()
                .title(movieDTO.getTitle())
                .director(movieDTO.getDirector())
                .releaseDate(movieDTO.getReleaseDate())
                .rating(movieDTO.getRating())
                .build();
    }
}
