package ra.com.session07.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.com.session07.model.dto.ProductDTO;
import ra.com.session07.model.entity.Product;
import ra.com.session07.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> findAll() {
        return ResponseEntity.ok(productService.findAll());
    }

    @PostMapping("/add")
    public ResponseEntity<Product> add(@RequestBody ProductDTO productDTO) {
        Product product = productService.addProduct(productDTO);
        if (product != null) {
            return new ResponseEntity<>(product, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable("id") Long id, @RequestBody ProductDTO productDTO) {
        Product product = productService.updateProduct(productDTO, id);
        if (product != null) {
            return new ResponseEntity<>(product, HttpStatus.OK);
        } else  {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        boolean rs = productService.deleteProductById(id);
        if (rs) {
            return new ResponseEntity<>("Delete product successfully", HttpStatus.OK);
        }  else {
            return new ResponseEntity<>("Delete product failed", HttpStatus.BAD_REQUEST);
        }
    }
}
