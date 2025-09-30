package ra.com.md05.session08.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.com.md05.session08.model.entity.Course;
import ra.com.md05.session08.model.entity.Enrollment;
import ra.com.md05.session08.model.entity.User;
import ra.com.md05.session08.repository.EnrollmentRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class EnrollmentService {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private CourseService courseService;

    public Enrollment registerCourse(User user, Long courseId) {
        Course course = courseService.findById(courseId);
        if (course == null) {
            return null;
        }else {
            Enrollment enrollment = new Enrollment();
            enrollment.setUser(user);
            enrollment.setCourse(course);
            enrollment.setCreatedDate(LocalDate.now());
            return enrollmentRepository.save(enrollment);
        }

    }

    public List<Enrollment> getRegisteredCourses(long userId) {
        return enrollmentRepository.findByUserId(userId) ;
    }
}
