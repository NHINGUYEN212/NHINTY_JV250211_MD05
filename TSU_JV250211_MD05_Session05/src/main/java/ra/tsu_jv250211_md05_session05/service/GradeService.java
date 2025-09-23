package ra.tsu_jv250211_md05_session05.service;

import ra.tsu_jv250211_md05_session05.model.dto.GradeDTO;
import ra.tsu_jv250211_md05_session05.model.entity.Grade;
import ra.tsu_jv250211_md05_session05.repository.GradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GradeService {

    @Autowired
    private GradeRepository gradeRepository;
    @Autowired
    private CourseService courseService;
    @Autowired
    private StudentService studentService;

    public List<Grade> findAll() {
        return gradeRepository.findAll();
    }

    public Grade findById(Long id) {
        return gradeRepository.findById(id).orElse(null);
    }

    public Grade save(GradeDTO gradeDTO) {
        return gradeRepository.save(convertGradeDTOToGrade(gradeDTO));
    }

    public Grade update(Long id, GradeDTO gradeDTO) {
        Grade grade = findById(id);
        if (grade != null) {
            Grade updateGrade = convertGradeDTOToGrade(gradeDTO);
            updateGrade.setId(id);
            return gradeRepository.save(updateGrade);
        }else {
            return null;
        }

    }

    public String delete(Long id) {
        Grade grade = findById(id);
        if (grade != null) {
            try {
                gradeRepository.delete(grade);
                return "Grade deleted";
            }catch (Exception e) {
                return "Grade not deleted";
            }
        }else {
            return "Grade not found " ;
        }
    }

    public Grade convertGradeDTOToGrade(GradeDTO gradeDTO) {
        return Grade
                .builder()
                .score(gradeDTO.getScore())
                .course(courseService.findById(gradeDTO.getCourseId()))
                .student(studentService.findById(gradeDTO.getStudentId()))
                .build();
    }
}
