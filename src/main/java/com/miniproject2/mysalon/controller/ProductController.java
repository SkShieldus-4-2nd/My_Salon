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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ObjectMapper objectMapper;
    
    @Value("${file.upload-dir}")
    private String uploadDir;

    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok(productService.createProduct(productDTO));
    }

    @PostMapping("/create")
    public ResponseEntity<ProductDTO> createProduct(@RequestBody CreateProductDTO.ProductRequest request) {
        return ResponseEntity.ok(productService.createProduct2(request));
    }

    @PostMapping(value = "/create-with-image", consumes = "multipart/form-data")
    public ResponseEntity<ProductDTO> createProductWithImage(
            @RequestParam("userNum") Long userNum,
            @RequestParam("productName") String productName,
            @RequestParam("price") Long price,
            @RequestParam("delivery_price") Long deliveryPrice,
            @RequestParam(value = "mainImage", required = false) MultipartFile mainImage,
            @RequestParam("description") String description,
            @RequestParam(value = "gender", required = false) String gender,
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "categoryLow", required = false) String categoryLow,
            @RequestParam("productDetails") String productDetailsJson) {
        
        try {
            CreateProductDTO.ProductRequest request = new CreateProductDTO.ProductRequest();
            request.setUserNum(userNum);
            request.setProductName(productName);
            request.setPrice(price);
            request.setDelivery_price(deliveryPrice);
            request.setDescription(description);
            
            // 이미지 파일명 설정 (기본값 설정, 나중에 저장 후 업데이트)
            request.setMainImage("default.jpg");
            
            // Enum 값들 설정
            if (gender != null && !gender.isEmpty()) {
                request.setGender(Gender.valueOf(gender));
            }
            if (category != null && !category.isEmpty()) {
                request.setCategory(Category.valueOf(category));
            }
            if (categoryLow != null && !categoryLow.isEmpty()) {
                request.setCategoryLow(CategoryLow.valueOf(categoryLow));
            }
            
            // ProductDetails JSON 파싱
            CreateProductDTO.ProductDetailDTO2[] productDetailsArray = 
                objectMapper.readValue(productDetailsJson, CreateProductDTO.ProductDetailDTO2[].class);
            request.setProductDetails(List.of(productDetailsArray));
            
            // 상품 먼저 생성
            ProductDTO createdProduct = productService.createProduct2(request);
            
            // 이미지가 있는 경우 저장
            if (mainImage != null && !mainImage.isEmpty()) {
                try {
                    // 상품별 디렉토리 생성: uploads/product/{productNum}/
                    Path uploadPath = Paths.get(uploadDir, "product", String.valueOf(createdProduct.getProductNum()));
                    if (!Files.exists(uploadPath)) {
                        Files.createDirectories(uploadPath);
                    }

                    // 파일명 생성: 원본파일명_상품번호.확장자
                    String originalFilename = mainImage.getOriginalFilename();
                    String extension = "";
                    if (originalFilename != null && originalFilename.contains(".")) {
                        extension = originalFilename.substring(originalFilename.lastIndexOf("."));
                    }
                    
                    String safeFilename = "main_" + createdProduct.getProductNum() + extension;
                    Path filePath = uploadPath.resolve(safeFilename);
                    
                    // 파일 저장
                    mainImage.transferTo(filePath.toFile());
                    
                    // 저장된 파일 경로 생성
                    String fileUrl = "/uploads/product/" + createdProduct.getProductNum() + "/" + safeFilename;
                    
                    // 상품의 mainImage 필드 업데이트
                    productService.updateProductImage(createdProduct.getProductNum(), fileUrl);
                    
                    // 응답에 업데이트된 이미지 경로 포함
                    createdProduct.setMainImage(fileUrl);
                    
                } catch (IOException e) {
                    throw new RuntimeException("이미지 저장 중 오류가 발생했습니다: " + e.getMessage(), e);
                }
            }
            
            return ResponseEntity.ok(createdProduct);
        } catch (Exception e) {
            throw new RuntimeException("상품 등록 중 오류가 발생했습니다: " + e.getMessage(), e);
        }
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

    @GetMapping("/{productId}/first-detail")
    public ResponseEntity<Long> getFirstProductDetailNum(@PathVariable Long productId) {
        return ResponseEntity.ok(productService.getFirstProductDetailNum(productId));
    }

}