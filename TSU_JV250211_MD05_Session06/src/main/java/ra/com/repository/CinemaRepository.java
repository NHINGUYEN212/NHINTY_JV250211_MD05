package ra.com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.com.model.entity.Cinema;

public interface CinemaRepository extends JpaRepository<Cinema, Long> {
}
