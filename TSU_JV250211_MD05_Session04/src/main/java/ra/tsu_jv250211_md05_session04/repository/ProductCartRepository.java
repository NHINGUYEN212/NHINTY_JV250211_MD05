package ra.tsu_jv250211_md05_session04.repository;

import ra.tsu_jv250211_md05_session04.model.entity.ProductCart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCartRepository extends JpaRepository<ProductCart, Long> {
    public ProductCart findProductCartByProductId(long productId);
}
