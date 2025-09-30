package ra.com.md05.session08.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.com.md05.session08.model.entity.Enrollment;

import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    List<Enrollment> findByUserId(Long userId);
}
