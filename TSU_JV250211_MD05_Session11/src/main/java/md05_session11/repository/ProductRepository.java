package md05_session11.repository;

import md05_session11.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("select p from Product p where (:searchName is null or p.productName like concat('%',:searchName,'%'))")
    Page<Product> findAllAndSearchName(@Param("searchName") String name, Pageable pageable);
}
