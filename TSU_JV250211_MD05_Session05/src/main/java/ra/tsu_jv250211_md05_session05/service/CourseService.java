package ra.tsu_jv250211_md05_session05.service;

import ra.tsu_jv250211_md05_session05.model.dto.CourseDTO;
import ra.tsu_jv250211_md05_session05.model.entity.Course;
import ra.tsu_jv250211_md05_session05.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    public Course findById(Long id) {
        return courseRepository.findById(id).orElse(null);
    }

    public Course save(CourseDTO courseDTO) {
        return courseRepository.save(convertCourseDTOToCourse(courseDTO));
    }

    public Course update(Long id, CourseDTO courseDTO) {

        Course course = findById(id);
        if (course != null) {
            Course updatedCourse = convertCourseDTOToCourse(courseDTO);
            updatedCourse.setId(id);
            try {
                return courseRepository.save(updatedCourse);

            }catch (Exception e) {
                return null;
            }
        }else {
            return null;
        }

    }

    public boolean delete(Long id) {
        Course course = findById(id);
        if (course != null) {
            try {
                courseRepository.deleteById(id);
                return true;
            }catch (NoSuchElementException e) {
                return false;
            }
        }else {
            return false;
        }

    }

    public Course convertCourseDTOToCourse(CourseDTO courseDTO) {
        return Course
                .builder()
                .courseName(courseDTO.getCourseName())
                .description(courseDTO.getDescription())
                .duration(courseDTO.getDuration())
                .build();
    }
}
