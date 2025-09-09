package com.miniproject2.mysalon.controller;

import com.miniproject2.mysalon.controller.dto.ProductDTO;
import com.miniproject2.mysalon.entity.Category;
import com.miniproject2.mysalon.entity.CategoryLow;
import com.miniproject2.mysalon.entity.Product;
import com.miniproject2.mysalon.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.searchProductsByCategoryAndCategoryLow(null, null);
        return ResponseEntity.ok(products);
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody ProductDTO productDTO) {
        Product createdProduct = productService.createProduct(productDTO);
        return ResponseEntity.ok(createdProduct);
    }

    @PostMapping("/{productId}")
    public ResponseEntity<Product> editProductPost(@PathVariable Long productId, @RequestBody ProductDTO productDTO) {
        Product editedProduct = productService.editProduct(productId, productDTO);
        return ResponseEntity.ok(editedProduct);
    }

    @PatchMapping("/{productId}")
    public ResponseEntity<Product> editProductPatch(@PathVariable Long productId, @RequestBody ProductDTO productDTO) {
        Product editedProduct = productService.editProduct(productId, productDTO);
        return ResponseEntity.ok(editedProduct);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search/name")
    public ResponseEntity<List<Product>> searchProductByProductName(@RequestParam String keyword) {
        List<Product> products = productService.searchProductByProductName(keyword);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/search/category")
    public ResponseEntity<List<Product>> searchProductsByCategoryAndCategoryLow(
            @RequestParam(required = false) Category category,
            @RequestParam(required = false) CategoryLow categoryLow) {
        List<Product> products = productService.searchProductsByCategoryAndCategoryLow(category, categoryLow);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/search/user/{userNum}")
    public ResponseEntity<List<Product>> searchProductsByUserNum(@PathVariable Long userNum) {
        List<Product> products = productService.searchProductsByUserNum(userNum);
        return ResponseEntity.ok(products);
    }
}
