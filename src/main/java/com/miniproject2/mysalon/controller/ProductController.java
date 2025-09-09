package com.miniproject2.mysalon.controller;

import com.miniproject2.mysalon.controller.dto.ProductDTO;
import com.miniproject2.mysalon.entity.Category;
import com.miniproject2.mysalon.entity.CategoryLow;
import com.miniproject2.mysalon.entity.Gender;
import com.miniproject2.mysalon.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok(productService.createProduct(productDTO));
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long productId, @RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok(productService.editProduct(productId, productDTO, false));
    }

    @PatchMapping("/{productId}")
    public ResponseEntity<ProductDTO> patchProduct(@PathVariable Long productId, @RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok(productService.editProduct(productId, productDTO, true));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> searchProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Category category,
            @RequestParam(required = false) CategoryLow categoryLow,
            @RequestParam(required = false) Gender gender,
            @RequestParam(required = false) Long userNum) {

        if (name != null) {
            return ResponseEntity.ok(productService.searchProductByProductName(name));
        } else if (userNum != null) {
            return ResponseEntity.ok(productService.searchProductsByUserNum(userNum));
        } else if (category != null || gender != null) {
            return ResponseEntity.ok(productService.searchProducts(category, categoryLow, gender));
        } else {
            return ResponseEntity.ok(productService.getAllProducts());
        }
    }
}