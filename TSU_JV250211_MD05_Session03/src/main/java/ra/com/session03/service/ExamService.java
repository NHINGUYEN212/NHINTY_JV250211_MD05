package ra.com.session03.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ra.com.session03.model.dto.ExamDTO;
import ra.com.session03.model.entity.Exam;
import ra.com.session03.repository.ExamRepository;

@Service
public class ExamService {
    @Autowired
    private ExamRepository examRepository;

    public Page<Exam> findAll(Pageable pageable, String search) {
        return examRepository.findAllAndSearch(pageable,search);
    }

    public Exam save(ExamDTO examDTO) {
        Exam exam = Exam
                .builder()
                .title(examDTO.getTitle())
                .description(examDTO.getDescription())
                .build();
        return examRepository.save(exam);
    }

    public Exam findById(long id) {
        return examRepository.findById(id).orElse(null);
    }
}