package ra.tsu_jv250211_md05_session04.model.dto;

import ra.tsu_jv250211_md05_session04.model.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductCartDTO {
    private Long productId;
    private int quantity;
}
