package com.miniproject2.mysalon.controller;

import com.miniproject2.mysalon.controller.dto.CreateProductDTO;
import com.miniproject2.mysalon.controller.dto.ProductDTO;
import com.miniproject2.mysalon.controller.dto.ProductSimpleDTO;
import com.miniproject2.mysalon.entity.Category;
import com.miniproject2.mysalon.entity.CategoryLow;
import com.miniproject2.mysalon.entity.Gender;
import com.miniproject2.mysalon.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('SELLER')")
    public ResponseEntity<ProductDTO> createProduct(@RequestBody CreateProductDTO.ProductRequest request) {
        return ResponseEntity.ok(productService.createProduct2(request));
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long productId, @RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok(productService.editProduct(productId, productDTO, false));
    }

    @PatchMapping("/{productId}")
    public ResponseEntity<ProductDTO> patchProduct(@PathVariable Long productId, @RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok(productService.editProduct(productId, productDTO, true));
    }
    @PatchMapping("/update/{productId}")
    public ResponseEntity<ProductDTO> updateProduct2(@PathVariable Long productId, @RequestBody CreateProductDTO.ProductRequest request) {
        return ResponseEntity.ok(productService.editProduct2(productId,request));
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

    @GetMapping("/{userId}")
    public ResponseEntity<List<ProductDTO>> getAllProductsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(productService.getAllProductsByUser(userId));
    }
    @GetMapping("/all-products")
    public ResponseEntity<List<ProductSimpleDTO.Response>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts2());
    }

    @GetMapping("/detail/{productId}")
    public ResponseEntity<ProductSimpleDTO.ProductDetailResponse> getProductById(@PathVariable Long productId) {
        return ResponseEntity.ok(productService.getProductById(productId));
    }

}