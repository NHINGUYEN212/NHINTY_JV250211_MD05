package ra.com.session03.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.com.session03.model.entity.Question;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question,Long> {
    List<Question> findAllByExam_Id(long examId);
}
