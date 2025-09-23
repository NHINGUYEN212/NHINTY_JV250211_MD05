package ra.tsu_jv250211_md05_session05.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.tsu_jv250211_md05_session05.model.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
