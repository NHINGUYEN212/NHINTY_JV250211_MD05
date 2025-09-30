package ra.com.md05.session08.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ra.com.md05.session08.model.dto.request.CourseDTO;
import ra.com.md05.session08.model.entity.Course;
import ra.com.md05.session08.repository.CourseRepository;

import java.util.NoSuchElementException;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public Course addCourse(CourseDTO courseDTO) {
        Course course = convertCourseDTOToCourse(courseDTO);
        try {
            return courseRepository.save(course);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public Course findById(Long id) {
        return courseRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Course not found"));
    }

    public Course updateCourse(Long id, CourseDTO courseDTO) {
        Course oldCourse = findById(id);
        if (oldCourse == null) {
            return null;
        }else {
            Course newCourse = convertCourseDTOToCourse(courseDTO);
            newCourse.setId(id);
            try {
                return courseRepository.save(newCourse);
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }

    }

    public boolean deleteCourse(Long id) {
        Course course = findById(id);
        if (course == null) {
            return false;
        }else {
            try {
                courseRepository.delete(course);
                return true;
            }catch (Exception e){
                e.printStackTrace();
                return false;
            }
        }
    }

    public Page<Course> getAllCourses(Pageable pageable) {
        return courseRepository.findAll(pageable);
    }

    public Course convertCourseDTOToCourse(CourseDTO courseDTO) {
        return Course
                .builder()
                .courseName(courseDTO.getCourseName())
                .duration(courseDTO.getDuration())
                .description(courseDTO.getDescription())
                .build();
    }
}
