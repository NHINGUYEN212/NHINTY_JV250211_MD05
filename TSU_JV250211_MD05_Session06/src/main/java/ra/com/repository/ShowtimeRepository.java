package ra.com.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ra.com.model.entity.Showtime;

public interface ShowtimeRepository extends JpaRepository<Showtime, Long> {
}
