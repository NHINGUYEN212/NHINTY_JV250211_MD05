package ra.com.md05.session08.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.com.md05.session08.model.dto.request.CourseDTO;
import ra.com.md05.session08.model.entity.Course;
import ra.com.md05.session08.service.CourseService;

import java.util.List;

@RestController
@RequestMapping("/admin/courses")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @GetMapping
    public ResponseEntity<Page<Course>> getAllCourses(@RequestParam(name = "page",defaultValue = "0") int page,
                                                      @RequestParam(name = "size" ,defaultValue = "5") int size) {
        return new ResponseEntity<>(courseService.getAllCourses(PageRequest.of(page, size)), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addCourse(@RequestBody CourseDTO courseDTO) {
        Course course = courseService.addCourse(courseDTO);
        if (course == null) {
            return new ResponseEntity<>("Add course failed", HttpStatus.BAD_REQUEST);
        }else {
            return new ResponseEntity<>(course, HttpStatus.CREATED);
        }
    }
    @PutMapping("/edit/{id}")
    public ResponseEntity<?> editCourse(@RequestBody CourseDTO courseDTO, @PathVariable long id) {
        Course course = courseService.updateCourse(id, courseDTO);
        if (course == null) {
            return new ResponseEntity<>("Edit course failed", HttpStatus.BAD_REQUEST);
        }else {
            return new ResponseEntity<>(course, HttpStatus.OK);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable long id) {
        boolean deleted = courseService.deleteCourse(id);
        if (deleted) {
            return new ResponseEntity<>("Delete course successful", HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Delete course failed", HttpStatus.BAD_REQUEST);
        }
    }
}