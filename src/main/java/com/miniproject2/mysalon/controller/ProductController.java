package com.miniproject2.mysalon.controller;

import com.miniproject2.mysalon.controller.dto.CreateProductDTO;
import com.miniproject2.mysalon.controller.dto.ProductDTO;
import com.miniproject2.mysalon.controller.dto.ProductSimpleDTO;
import com.miniproject2.mysalon.entity.Category;
import com.miniproject2.mysalon.entity.CategoryLow;
import com.miniproject2.mysalon.entity.Gender;
import com.miniproject2.mysalon.security.CurrentUser;
import com.miniproject2.mysalon.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @PreAuthorize("hasAuthority('SELLER')")
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok(productService.createProduct(productDTO));
    }

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('SELLER')")
    public ResponseEntity<ProductDTO> createProduct(
            @CurrentUser Long userNum,
            @ModelAttribute CreateProductDTO.ProductRequest request,
            @RequestPart(value = "mainImageFile", required = false) MultipartFile mainImageFile) {

        return ResponseEntity.ok(productService.createProduct2(userNum, request, mainImageFile));
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long productId, @RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok(productService.editProduct(productId, productDTO, false));
    }

    @PatchMapping("/{productId}")
    public ResponseEntity<ProductDTO> patchProduct(@PathVariable Long productId, @RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok(productService.editProduct(productId, productDTO, true));
    }


    @PatchMapping(value = "/update/{productId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductDTO> updateProduct2(@CurrentUser Long userNum,@PathVariable Long productId, @RequestBody CreateProductDTO.ProductRequest request,
                                                     @RequestPart(value = "mainImageFile", required = false) MultipartFile mainImageFile) {
        return ResponseEntity.ok(productService.editProduct2(userNum, productId,request, mainImageFile));
    }


    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    @PreAuthorize("hasAuthority('SELLER') or hasAuthority('BUYER')")
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