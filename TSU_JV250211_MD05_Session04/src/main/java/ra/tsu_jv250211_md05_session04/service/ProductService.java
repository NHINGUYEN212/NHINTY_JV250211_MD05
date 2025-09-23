package ra.tsu_jv250211_md05_session04.service;

import  ra.tsu_jv250211_md05_session04.model.dto.ProductDTO;
import  ra.tsu_jv250211_md05_session04.model.entity.Product;
import  ra.tsu_jv250211_md05_session04.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProductService {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    public Product findById(long id) {
        return productRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Product not found"));
    }

    public String save(ProductDTO productDTO) {
        try {
            productRepository.save(convertDTOToProduct(productDTO));
            return "Thêm mới sản phẩm thành công !";
        } catch (Exception e) {
            return "Thêm mới sản phẩm thất bại !" ;
        }

    }

    public String update(ProductDTO productDTO, long id) {
        Product product = findById(id);
        if(product != null) {
            Product updatedProduct = convertDTOToProduct(productDTO);
            updatedProduct.setId(id);
            try {
                productRepository.save(updatedProduct);
                return "Cập nhật sản phẩm thành công !";
            } catch (Exception e) {
                return "Cập nhật sản phẩm thất bại !" ;
            }

        }else {
            return  "Product not found";
        }
    }

    public String delete(long id) {
        Product product = findById(id);
        if(product != null) {
            try {
                productRepository.delete(product);
                return "Xóa sản phẩm thành công !";
            } catch (Exception e) {
                return "Xóa sản phẩm thất bại ! " ;
            }


        }else {
            return "Product not found";
        }
    }

    public Product convertDTOToProduct(ProductDTO productDTO) {
        return Product
                .builder()
                .productName(productDTO.getProductName())
                .price(productDTO.getPrice())
                .stock(productDTO.getStock())
                .category(categoryService.getCategoryById(productDTO.getCategoryId()))
                .build();
    }
}
