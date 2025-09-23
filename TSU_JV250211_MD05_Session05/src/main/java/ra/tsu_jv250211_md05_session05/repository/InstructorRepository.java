package ra.tsu_jv250211_md05_session05.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.tsu_jv250211_md05_session05.model.entity.Instructor;

public interface InstructorRepository extends JpaRepository<Instructor, Long> {
}