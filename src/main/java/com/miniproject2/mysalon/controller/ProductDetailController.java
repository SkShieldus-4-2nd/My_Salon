package com.miniproject2.mysalon.controller;

import com.miniproject2.mysalon.controller.dto.ProductDetailDTO;
import com.miniproject2.mysalon.service.ProductDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product-details")
public class ProductDetailController {

    private final ProductDetailService productDetailService;

    @GetMapping
    public ResponseEntity<List<ProductDetailDTO>> getAllProductDetails() {
        List<ProductDetailDTO> dtoList = productDetailService.getAllProductDetails();
        return ResponseEntity.ok(dtoList);
    }

    @PostMapping
    public ResponseEntity<ProductDetailDTO> createProductDetail(@RequestBody ProductDetailDTO dto) {
        ProductDetailDTO createdDto = productDetailService.createProductDetail(dto);
        return ResponseEntity.created(URI.create("/product-details/" + createdDto.getProductDetailNum()))
                .body(createdDto);
    }

    @PutMapping("/{productDetailNum}")
    public ResponseEntity<ProductDetailDTO> updateProductDetail(@PathVariable Long productDetailNum, @RequestBody ProductDetailDTO dto) {
        ProductDetailDTO updatedDto = productDetailService.updateProductDetail(productDetailNum, dto);
        return ResponseEntity.ok(updatedDto);
    }

    @DeleteMapping("/{productDetailNum}")
    public ResponseEntity<Void> deleteProductDetail(@PathVariable Long productDetailNum) {
        productDetailService.deleteProductDetail(productDetailNum);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{productDetailNum}")
    public ResponseEntity<ProductDetailDTO> getProductDetailByDetailNum(@PathVariable Long productDetailNum) {
        ProductDetailDTO dto = productDetailService.searchProductDetailByProductDetailNum(productDetailNum);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/product/{productNum}")
    public ResponseEntity<List<ProductDetailDTO>> getProductDetailsByProductNum(@PathVariable Long productNum) {
        List<ProductDetailDTO> dtoList = productDetailService.searchProductDetailByProductNum(productNum);
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/user/{userNum}")
    public ResponseEntity<List<ProductDetailDTO>> getProductDetailsByUserNum(@PathVariable Long userNum) {
        List<ProductDetailDTO> dtoList = productDetailService.searchProductDetailByUserNum(userNum);
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/search/by-color")
    public ResponseEntity<List<ProductDetailDTO>> getProductDetailsByColor(@RequestParam String color) {
        List<ProductDetailDTO> dtoList = productDetailService.searchProductDetailByColor(color);
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/search/by-color-and-size")
    public ResponseEntity<List<ProductDetailDTO>> getProductDetailsByColorAndSize(@RequestParam String color, @RequestParam String size) {
        List<ProductDetailDTO> dtoList = productDetailService.searchProductDetailByColorAndSize(color, size);
        return ResponseEntity.ok(dtoList);
    }
}
