package ra.com.session07.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

public class ProductDTO {
    private String name;
    private Double price;
    private String description;
    private Integer stock;
}
