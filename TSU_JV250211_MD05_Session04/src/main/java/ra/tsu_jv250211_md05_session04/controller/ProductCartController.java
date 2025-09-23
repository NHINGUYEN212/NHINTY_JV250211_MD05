package ra.tsu_jv250211_md05_session04.controller;

import ra.tsu_jv250211_md05_session04.model.dto.ProductCartDTO;
import ra.tsu_jv250211_md05_session04.model.entity.ProductCart;
import ra.tsu_jv250211_md05_session04.service.ProductCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carts")
public class ProductCartController {
    @Autowired
    private ProductCartService productCartService;

    @GetMapping
    public List<ProductCart> getCarts(){
        return productCartService.getProductCarts();
    }

    @PostMapping("/add")
    public String addToCart(@RequestBody ProductCartDTO productCartDTO){
        return productCartService.addToCart(productCartDTO);
    }

    @PutMapping("/edit/{id}")
    public String editCart(@RequestParam("quantity") int quantity, @PathVariable int id){
        return productCartService.updateProductCart(quantity, id);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteCart(@PathVariable int id){
        return productCartService.deleteProductCartById(id);
    }
}