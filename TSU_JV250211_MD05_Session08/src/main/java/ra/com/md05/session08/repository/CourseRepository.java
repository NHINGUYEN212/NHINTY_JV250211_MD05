package ra.com.md05.session08.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.com.md05.session08.model.entity.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
