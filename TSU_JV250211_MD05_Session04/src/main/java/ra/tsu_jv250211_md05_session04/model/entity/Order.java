package ra.tsu_jv250211_md05_session04.model.entity;

import ra.tsu_jv250211_md05_session04.model.constant.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String customerName;
    private String phoneNumber;
    private String address;
    private double totalAmount;
    private LocalDateTime createdDate ;
    private Status status;


}
