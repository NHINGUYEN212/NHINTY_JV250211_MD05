package ra.tsu_jv250211_md05_session04.service;

import ra.tsu_jv250211_md05_session04.model.constant.Status;
import ra.tsu_jv250211_md05_session04.model.dto.OrderDTO;
import ra.tsu_jv250211_md05_session04.model.entity.Order;
import ra.tsu_jv250211_md05_session04.model.entity.OrderDetail;
import ra.tsu_jv250211_md05_session04.model.entity.Product;
import ra.tsu_jv250211_md05_session04.model.entity.ProductCart;
import ra.tsu_jv250211_md05_session04.repository.OrderDetailRepository;
import ra.tsu_jv250211_md05_session04.repository.OrderRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductCartService productCartService;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Order findById(long id) {
        return orderRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Order not found"));
    }

    public String save(OrderDTO orderDTO) {
        List<ProductCart> productCarts = productCartService.getProductCarts();
        if(productCarts.isEmpty()) {
            return null;
        }else {
            double totalMoney = productCarts.stream().map(productCart -> productCart.getProduct().getPrice() * productCart.getQuantity())
                    .reduce(0.0, Double::sum);
            Order order = convertDTOToOrder(orderDTO);
            order.setTotalAmount(totalMoney);
            order.setCreatedDate(LocalDateTime.now());
            order.setStatus(Status.PENDING);
            Order newOrder = orderRepository.save(order);
            if (newOrder != null) {
                for (ProductCart productCart : productCarts) {
                    OrderDetail orderDetail = OrderDetail
                            .builder()
                            .order(newOrder)
                            .product(productCart.getProduct())
                            .priceBuy(productCart.getProduct().getPrice())
                            .quantity(productCart.getQuantity())
                            .build();
                    // lưu đơn hàng vào cơ sở dữ liệu
                    orderDetailRepository.save(orderDetail);
                    Product product = productService.findById(productCart.getProduct().getId());
                    product.setStock(product.getStock() - productCart.getQuantity());
                    // cập nhật lại stock
                    productService.saveProduct(product);
                }
                // xóa tất cả sản phẩm trong giỏ hàng
                productCartService.deleteAllProductCarts();
                return "Thêm mới đơn hàng thành công !" ;
            }else {
                return "Thêm mới đơn hàng thất bại ! ";
            }


        }

    }

    public String changeStatus(long id) {
        Order order = findById(id);
        if(order == null || order.getStatus() == Status.SUCCESS || order.getStatus() == Status.CANCEL) {
            return "Không thể đổi trạng thái của đơn hàng đã hoàn thành hoặc bị hủy !";
        }else {
            if(order.getStatus() == Status.PENDING) {
                order.setStatus(Status.CONFIRM);
            } else if (order.getStatus() == Status.CONFIRM) {
                order.setStatus(Status.DELIVERING);
            }else if (order.getStatus() == Status.DELIVERING) {
                order.setStatus(Status.SUCCESS);
            }
            Order updateOrder = orderRepository.save(order);
            if (updateOrder != null) {
                return "Cập nhật trạng thái đơn hàng thành công !";
            }else {
                return "Cập nhật trạng thái đơn hàng thất bại !" ;
            }
        }
    }

    public String cancelOrder(long id) {
        Order order = findById(id);
        if(order == null || order.getStatus() == Status.SUCCESS || order.getStatus() == Status.CANCEL) {
            return "Không thể hủy đơn hàng đã hoàn thành hoặc bị hủy !";
        }else {
            order.setStatus(Status.CANCEL);
            Order updateOrder = orderRepository.save(order);
            if (updateOrder != null) {
                return "Hủy đơn hàng thành công !";
            }else {
                return "Hủy đơn hàng thất bại !" ;
            }
        }
    }

    public Order convertDTOToOrder(OrderDTO orderDTO) {
        return Order
                .builder()
                .customerName(orderDTO.getCustomerName())
                .address(orderDTO.getAddress())
                .phoneNumber(orderDTO.getPhoneNumber())
                .build();
    }
}