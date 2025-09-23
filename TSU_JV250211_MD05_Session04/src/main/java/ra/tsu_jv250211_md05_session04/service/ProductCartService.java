package ra.tsu_jv250211_md05_session04.service;

import ra.tsu_jv250211_md05_session04.model.dto.ProductCartDTO;
import ra.tsu_jv250211_md05_session04.model.entity.ProductCart;
import ra.tsu_jv250211_md05_session04.repository.ProductCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProductCartService {
    @Autowired
    private ProductCartRepository productCartRepository;

    @Autowired
    private ProductService productService;

    public List<ProductCart> getProductCarts() {
        return productCartRepository.findAll();
    }

    public ProductCart getProductCartById(long id) {
        return productCartRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Product cart not found"));
    }

    public ProductCart findProductCartByProductId(long productId) {
        return productCartRepository.findProductCartByProductId(productId);
    }

    public String addToCart(ProductCartDTO productCartDTO){
        ProductCart productCart = findProductCartByProductId(productCartDTO.getProductId());
        if(productCart == null){
            ProductCart newProductCart = productCartRepository.save(ProductCart
                    .builder()
                    .product(productService.findById(productCartDTO.getProductId()))
                    .quantity(productCartDTO.getQuantity())
                    .build());
            if (newProductCart == null) {
                return "Thêm sản phẩm vào giỏ hàng thất bại !";
            }else {
                return  "Thêm mới sản phẩm vào giỏ hàng thành công !" ;
            }
        }else {
            productCart.setQuantity(productCart.getQuantity() + productCartDTO.getQuantity());
            ProductCart updateProductCart = productCartRepository.save(productCart);
            if (updateProductCart == null) {
                return "Thêm sản phẩm vào giỏ hàng thất bại !" ;
            }else {
                return "Cập nhật số lượng thành công !" ;
            }
        }
    }

    public String updateProductCart(int quantity,long id){
        if(quantity <= 0){
            return "Số lượng sản phẩm phải lớn hơn 0 !";
        }
        ProductCart productCart = getProductCartById(id);
        if(productCart == null){
            throw new NoSuchElementException("Product cart not found");
        }else {
            productCart.setQuantity(quantity);
            productCartRepository.save(productCart);
            return "Cập nhật số lượng thành công !" ;
        }
    }

    public void deleteAllProductCarts(){
        productCartRepository.deleteAll();
    }

    public String deleteProductCartById(long id){
        ProductCart productCart = getProductCartById(id);
        if(productCart == null){
            throw new NoSuchElementException("Product cart not found");
        }else {
            productCartRepository.delete(productCart);
            return "Đã xóa sản phẩm ra khỏi giỏ hàng !";
        }
    }

}
