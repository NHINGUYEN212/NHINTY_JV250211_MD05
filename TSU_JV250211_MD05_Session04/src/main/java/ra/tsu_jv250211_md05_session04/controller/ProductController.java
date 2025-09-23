package ra.tsu_jv250211_md05_session04.controller;

import ra.tsu_jv250211_md05_session04.model.dto.ProductDTO;
import ra.tsu_jv250211_md05_session04.model.entity.Product;
import ra.tsu_jv250211_md05_session04.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Product> getProducts(){
        return productService.findAll();
    }

    @PostMapping("/add")
    public String addProduct(@RequestBody ProductDTO productDTO){
        return productService.save(productDTO);
    }

    @PutMapping("/edit/{id}")
    public String updateProduct(@PathVariable long id ,@RequestBody ProductDTO productDTO){
        return productService.update(productDTO,id);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteProduct(@PathVariable long id){
        return productService.delete(id);
    }
}
