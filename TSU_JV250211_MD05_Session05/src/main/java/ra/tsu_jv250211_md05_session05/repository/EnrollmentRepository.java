package ra.tsu_jv250211_md05_session05.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import ra.tsu_jv250211_md05_session05.model.entity.Enrollment;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
}