package ra.tsu_jv250211_md05_session02.repository;


import ra.tsu_jv250211_md05_session02.model.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface MovieRepository extends JpaRepository<Movie, Long> {
    @Query("select count(m) from Movie m where m.title = :title")
    long countByTitle(@Param("title") String title);


}