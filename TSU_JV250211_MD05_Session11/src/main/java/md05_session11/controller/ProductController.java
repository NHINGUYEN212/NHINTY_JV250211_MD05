package md05_session11.controller;

import jakarta.validation.Valid;
import md05_session11.model.dto.request.ProductDTO;
import md05_session11.model.dto.response.DataResponse;
import md05_session11.model.entity.Product;
import md05_session11.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<DataResponse<Page<Product>>> findAllAndSearch(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size,
            @RequestParam(name = "searchName", required = false) String searchName) {

        return productService.findAllAndSearch(PageRequest.of(page, size), searchName);
    }

    @PostMapping("/add")
    public ResponseEntity<DataResponse<?>> addProduct(@Valid @ModelAttribute ProductDTO productDTO,
                                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return buildErrorResponse(bindingResult, "Add product failed! Validation errors.");
        }

        return productService.addProduct(productDTO);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<DataResponse<?>> updateProduct(@Valid @ModelAttribute ProductDTO productDTO,
                                                         BindingResult bindingResult,
                                                         @PathVariable Long id) {
        if (bindingResult.hasErrors()) {
            return buildErrorResponse(bindingResult, "Update product failed! Validation errors.");
        }

        return productService.updateProduct(productDTO, id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<DataResponse<String>> deleteProduct(@PathVariable Long id) {
        return productService.deleteProduct(id);
    }

    private ResponseEntity<DataResponse<?>> buildErrorResponse(BindingResult bindingResult, String message) {
        Map<String, String> errorMap = new HashMap<>();
        bindingResult.getFieldErrors().forEach(error -> errorMap.put(error.getField(), error.getDefaultMessage()));

        DataResponse<Map<String, String>> errorResponse = DataResponse
                .<Map<String, String>>builder()
                .message(message)
                .data(errorMap)
                .status(400)
                .build();
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @GetMapping("/productSuggestions")
    public ResponseEntity<DataResponse<List<Product>>> getProductSuggestions() {
        return productService.getProductSuggestions();
    }

}