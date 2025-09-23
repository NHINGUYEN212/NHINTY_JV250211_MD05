package ra.tsu_jv250211_md05_session04.model.dto;
import ra.tsu_jv250211_md05_session04.model.entity.Category;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductDTO {
    private String productName;
    private double price;
    private int stock;
    private long categoryId;
}
