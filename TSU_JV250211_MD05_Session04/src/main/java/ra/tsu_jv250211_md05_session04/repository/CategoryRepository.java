package ra.tsu_jv250211_md05_session04.repository;


import ra.tsu_jv250211_md05_session04.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
