package ra.com.session07.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.com.session07.model.dto.ProductDTO;
import ra.com.session07.model.entity.Product;
import ra.com.session07.repository.ProductRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }
    public Product findById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Product not found"));
    }

    public Product addProduct(ProductDTO productDTO) {

        Product product = convertProductDTOToEntity(productDTO);
        try {
            return product = productRepository.save(product);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Product updateProduct(ProductDTO productDTO, Long id) {
        Product product = findById(id);
        if (product != null) {
            product.setName(productDTO.getName());
            product.setDescription(productDTO.getDescription());
            product.setPrice(productDTO.getPrice());
            product.setStock(productDTO.getStock());
            try {
                return productRepository.save(product);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } else return null;

    }

    public boolean deleteProductById(Long id) {
        Product product = findById(id);
        if (product != null) {
            try {
                productRepository.delete(product);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }  else return false;
    }

    public Product convertProductDTOToEntity(ProductDTO productDTO) {
        return Product.builder()
                .name(productDTO.getName())
                .description(productDTO.getDescription())
                .price(productDTO.getPrice())
                .stock(productDTO.getStock())
                .build();
    }
}
