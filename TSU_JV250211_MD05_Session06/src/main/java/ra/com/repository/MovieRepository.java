package ra.com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.com.model.entity.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}
