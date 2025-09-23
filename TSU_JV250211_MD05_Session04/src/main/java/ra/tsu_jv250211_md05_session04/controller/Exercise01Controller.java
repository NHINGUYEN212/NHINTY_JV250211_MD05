package ra.tsu_jv250211_md05_session04.controller;

import ra.tsu_jv250211_md05_session04.model.dto.ProductList;
import ra.tsu_jv250211_md05_session04.model.entity.ProductExercise01;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/products")
public class Exercise01Controller {

    List<ProductExercise01> products = Arrays.asList(
            new ProductExercise01(1,"Product 1" , 10000),
            new ProductExercise01(2,"Product 2" , 15000),
            new ProductExercise01(3,"Product 3" , 20000)
    );

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE , value = "/json")
    public List<ProductExercise01> getProducts() {
        return products;
    }

    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE , value = "/xml")
    public ProductList getProducts2() {
        return new ProductList(products);
    }
}
