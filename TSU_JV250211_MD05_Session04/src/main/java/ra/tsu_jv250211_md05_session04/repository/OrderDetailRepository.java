package ra.tsu_jv250211_md05_session04.repository;

import ra.tsu_jv250211_md05_session04.model.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
}
