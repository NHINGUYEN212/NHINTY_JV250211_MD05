package md05_session11.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductDTO {
    @NotBlank(message = "Product name can not blank")
    private String productName;

    @NotNull(message = "Price can not null")
    private double price;

    @NotNull(message = "Stock can not null")
    private int stock;

    private MultipartFile image;
}
