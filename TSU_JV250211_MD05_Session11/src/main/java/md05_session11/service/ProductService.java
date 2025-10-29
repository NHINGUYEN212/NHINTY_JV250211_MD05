package md05_session11.service;

import md05_session11.model.dto.request.ProductDTO;
import md05_session11.model.dto.response.DataResponse;
import md05_session11.model.entity.Product;
import md05_session11.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.apache.commons.io.input.ReversedLinesFileReader;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CloudinaryService cloudinaryService;
    private final Logger logger = LoggerFactory.getLogger(ProductService.class);

    public ResponseEntity<DataResponse<?>> addProduct(ProductDTO productDTO) {
        if (productDTO.getImage() == null || productDTO.getImage().isEmpty()) {
            logger.error("Error: Image file is null or empty.");
            DataResponse<String> errorResponse = DataResponse.<String>builder()
                    .message("Image can not be null or empty.")
                    .data(null)
                    .status(400)
                    .build();
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        try {
            Product product = convertDTOtoEntity(productDTO);
            String imageURL = cloudinaryService.upload(productDTO.getImage());
            product.setImage(imageURL);
            Product newProduct = productRepository.save(product);

            DataResponse<Product> dataResponse = DataResponse.<Product>builder()
                    .message("Product saved successfully!")
                    .data(newProduct)
                    .status(201)
                    .build();
            logger.info("Product saved successfully!");
            return new ResponseEntity<>(dataResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error adding product: " + e.getMessage());
            e.printStackTrace();
            DataResponse<String> errorResponse = DataResponse.<String>builder()
                    .message("Failed to add product: " + e.getMessage())
                    .data(null)
                    .status(500)
                    .build();
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public Product convertDTOtoEntity(ProductDTO productDTO) {
        return Product.builder()
                .productName(productDTO.getProductName())
                .price(productDTO.getPrice())
                .stock(productDTO.getStock())
                .build();
    }

    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Product not found with id:" + id));
    }

    public ResponseEntity<DataResponse<?>> updateProduct(ProductDTO productDTO, Long id) {
        try {
            Product oldProduct = findById(id);

            Product product = convertDTOtoEntity(productDTO);
            product.setId(id);

            if (productDTO.getImage() != null && !productDTO.getImage().isEmpty()) {
                String imageURL = cloudinaryService.upload(productDTO.getImage());
                product.setImage(imageURL);
            } else {
                product.setImage(oldProduct.getImage());
            }

            Product updatedProduct = productRepository.save(product);
            DataResponse<Product> dataResponse = DataResponse.<Product>builder()
                    .message("Product updated successfully!")
                    .data(updatedProduct)
                    .status(200)
                    .build();
            logger.info("Product updated successfully: " + updatedProduct.getProductName());
            return new ResponseEntity<>(dataResponse, HttpStatus.OK);

        } catch (NoSuchElementException e) {
            logger.error("Update failed: " + e.getMessage());
            DataResponse<String> errorResponse = DataResponse.<String>builder()
                    .message(e.getMessage())
                    .data(null)
                    .status(404)
                    .build();
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Update product failed: " + e.getMessage());
            e.printStackTrace();
            DataResponse<String> errorResponse = DataResponse.<String>builder()
                    .message("An internal server error occurred: " + e.getMessage())
                    .data(null)
                    .status(500)
                    .build();
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<DataResponse<String>> deleteProduct(Long id) {
        try {
            Product product = findById(id);

            productRepository.delete(product);
            DataResponse<String> dataResponse = DataResponse.<String>builder()
                    .message("Delete product successfully!")
                    .data("Product with id " + id + " has been deleted.")
                    .status(200)
                    .build();
            logger.info("Delete product with id: " + id);
            return new ResponseEntity<>(dataResponse, HttpStatus.OK);

        } catch (NoSuchElementException e) {
            logger.error("Delete failed: " + e.getMessage());
            DataResponse<String> dataResponse = DataResponse.<String>builder()
                    .message(e.getMessage())
                    .data(null)
                    .status(404)
                    .build();
            return new ResponseEntity<>(dataResponse, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Delete product failed: " + e.getMessage());
            e.printStackTrace();
            DataResponse<String> dataResponse = DataResponse.<String>builder()
                    .message("Delete failed: " + e.getMessage())
                    .data(null)
                    .status(500)
                    .build();
            return new ResponseEntity<>(dataResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<DataResponse<Page<Product>>> findAllAndSearch(Pageable pageable, String searchName) {

        if (searchName != null && !searchName.isEmpty()) {
            logger.info("Search product : " + searchName);
        }

        Page<Product> page = productRepository.findAllAndSearchName(searchName, pageable);
        DataResponse<Page<Product>> dataResponse = DataResponse
                .<Page<Product>>builder()
                .message("List of products found successfully!")
                .data(page)
                .status(200)
                .build();
        logger.info("Find all and search products successful: " + page.getContent().size() + " product!");
        return new ResponseEntity<>(dataResponse, HttpStatus.OK);
    }

    public String findLastSearchProductLog() {
        File file = new File("app.log");
        if (!file.exists()) {
            logger.error("Log file 'app.log' does not exist.");
            return null;
        }

        final String SEARCH_PREFIX = "Search product :";

        try (ReversedLinesFileReader reader = new ReversedLinesFileReader(file, Charset.defaultCharset())) {
            String line;
            while ((line = reader.readLine()) != null) {

                int prefixIndex = line.indexOf(SEARCH_PREFIX);

                if (prefixIndex != -1) {
                    String searchTerm = line.substring(prefixIndex + SEARCH_PREFIX.length()).trim();

                    if (!searchTerm.isEmpty()) {
                        logger.info("Found last search log: " + searchTerm);
                        return searchTerm;
                    }
                }
            }
        } catch (IOException e) {
            logger.error("Error reading log file: " + e.getMessage());
            return null;
        }

        logger.warn("No 'Search product :' log found in app.log.");
        return null;
    }

    public ResponseEntity<DataResponse<List<Product>>> getProductSuggestions() {
        String lastSearchTerm = findLastSearchProductLog();

        if (lastSearchTerm == null) {
            DataResponse<List<Product>> response = DataResponse.<List<Product>>builder()
                    .message("No search log found.")
                    .data(Collections.emptyList())
                    .status(200)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        Pageable pageable = PageRequest.of(0, 10);

        List<Product> products = productRepository.findAllAndSearchName(lastSearchTerm, pageable).getContent();

        DataResponse<List<Product>> dataResponse = DataResponse
                .<List<Product>>builder()
                .message("Product suggestions based on last search: " + lastSearchTerm)
                .data(products)
                .status(200)
                .build();
        return new ResponseEntity<>(dataResponse, HttpStatus.OK);
    }
}