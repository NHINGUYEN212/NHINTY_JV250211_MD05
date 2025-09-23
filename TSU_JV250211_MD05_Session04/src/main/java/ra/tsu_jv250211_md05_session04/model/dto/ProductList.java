package ra.tsu_jv250211_md05_session04.model.dto;

import ra.tsu_jv250211_md05_session04.model.entity.ProductExercise01;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@JacksonXmlRootElement(localName = "products")
public class ProductList {
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<ProductExercise01> product;
}
