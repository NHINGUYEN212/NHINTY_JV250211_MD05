package ra.com.session07.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.com.session07.model.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
