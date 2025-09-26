package ra.com.session03.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ra.com.session03.model.entity.Exam;

public interface ExamRepository extends JpaRepository<Exam, Long> {
    @Query("select e from Exam e where (:search is null or e.title like concat('%',:search,'%') )")
    Page<Exam> findAllAndSearch(Pageable pageable, @Param("search") String search);
}
