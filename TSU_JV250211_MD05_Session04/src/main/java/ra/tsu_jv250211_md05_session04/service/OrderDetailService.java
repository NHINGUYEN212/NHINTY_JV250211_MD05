package ra.tsu_jv250211_md05_session04.service;

import ra.tsu_jv250211_md05_session04.model.entity.OrderDetail;
import ra.tsu_jv250211_md05_session04.repository.OrderDetailRepository;
import ra.tsu_jv250211_md05_session04.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailService {
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    public void save(OrderDetail orderDetail) {
        orderDetailRepository.save(orderDetail);
    }
}
