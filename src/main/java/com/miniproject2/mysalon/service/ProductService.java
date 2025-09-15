package com.miniproject2.mysalon.service;

import com.miniproject2.mysalon.controller.dto.CreateProductDTO;
import com.miniproject2.mysalon.controller.dto.ProductDTO;
import com.miniproject2.mysalon.controller.dto.ProductSimpleDTO;
import com.miniproject2.mysalon.entity.*;
import com.miniproject2.mysalon.exception.BusinessException;
import com.miniproject2.mysalon.exception.EntityNotFoundException;
import com.miniproject2.mysalon.exception.ErrorCode;
import com.miniproject2.mysalon.repository.ProductDetailRepository;
import com.miniproject2.mysalon.repository.ProductRepository;
import com.miniproject2.mysalon.repository.UserRepository;
import io.jsonwebtoken.io.IOException;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductDetailRepository productDetailRepository;
    private final UserRepository userRepository;

    @Value("${file.upload-dir}")
    private String uploadDir1;

    public ProductDTO createProduct(ProductDTO productDTO) {
        User user = userRepository.findById(productDTO.getUserNum())
                .orElseThrow(() -> new EntityNotFoundException("User", productDTO.getUserNum()));
        Product product = productDTO.toEntity(user);
        Product savedProduct = productRepository.save(product);
        return ProductDTO.fromEntity(savedProduct);
    }

    public ProductDTO createProduct2(Long userNum, CreateProductDTO.ProductRequest request, MultipartFile mainImageFile) {
        User user = userRepository.findById(userNum)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        String originalFileName = null;
        if (mainImageFile != null && !mainImageFile.isEmpty()) {
            try {
                // 고유한 파일명 생성 (예: UUID 사용)
                originalFileName = UUID.randomUUID().toString() + "_" + mainImageFile.getOriginalFilename();
                String uploadDir = "C:/uploads/";
                Path filePath = Paths.get(uploadDir + originalFileName);

                // 파일 저장
                Files.copy(mainImageFile.getInputStream(), filePath);
            } catch (java.io.IOException e) {
                throw new RuntimeException("상품 이미지 업로드 실패", e);
            }
        }

        // toEntity 메서드에 파일 이름 전달
        Product product = request.toEntity(user, originalFileName);
        Product savedProduct = productRepository.save(product);

        return ProductDTO.fromEntity(savedProduct);
    }

    public ProductDTO editProduct(Long productId, ProductDTO productDTO, boolean isPatch) {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product", productId));

        if (isPatch) {
            // Partial update
            if (StringUtils.hasText(productDTO.getProductName())) existingProduct.setProductName(productDTO.getProductName());
            if (productDTO.getPrice() != null) existingProduct.setPrice(productDTO.getPrice());
            if (StringUtils.hasText(productDTO.getMainImage())) existingProduct.setMainImage(productDTO.getMainImage());
            if (StringUtils.hasText(productDTO.getDescription())) existingProduct.setDescription(productDTO.getDescription());
            if (productDTO.getGender() != null) existingProduct.setGender(productDTO.getGender());
            if (productDTO.getCategory() != null) existingProduct.setCategory(productDTO.getCategory());
            if (productDTO.getCategoryLow() != null) existingProduct.setCategoryLow(productDTO.getCategoryLow());
        } else {
            // Full update
            User user = userRepository.findById(productDTO.getUserNum())
                    .orElseThrow(() -> new EntityNotFoundException("User", productDTO.getUserNum()));
            existingProduct.setUser(user);
            existingProduct.setProductName(productDTO.getProductName());
            existingProduct.setPrice(productDTO.getPrice());
            existingProduct.setMainImage(productDTO.getMainImage());
            existingProduct.setDescription(productDTO.getDescription());
            existingProduct.setGender(productDTO.getGender());
            existingProduct.setCategory(productDTO.getCategory());
            existingProduct.setCategoryLow(productDTO.getCategoryLow());
        }

        if (productDTO.getProductDetails() != null && !productDTO.getProductDetails().isEmpty()) {
            productDetailRepository.deleteAll(existingProduct.getProductDetails());
            List<ProductDetail> newDetails = productDTO.getProductDetails().stream()
                    .map(detailDTO -> detailDTO.toEntity(existingProduct))
                    .collect(Collectors.toList());
            existingProduct.getProductDetails().clear();
            existingProduct.getProductDetails().addAll(newDetails);
        }

        Product updatedProduct = productRepository.save(existingProduct);
        return ProductDTO.fromEntity(updatedProduct);
    }

    public ProductDTO editProduct2(Long userNum, Long productId, CreateProductDTO.ProductRequest request, MultipartFile mainImage) {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));

        User user = userRepository.findById(userNum)
                    .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        deleteProduct(productId);
        return createProduct2(userNum, request, mainImage);

    }

    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));
        productRepository.delete(product);
    }

    public List<ProductDTO> searchProductByProductName(String name) {
        Specification<Product> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            String[] keywords = name.split("\\s+");
            for (String keyword : keywords) {
                predicates.add(criteriaBuilder.like(root.get("productName"), "%" + keyword + "%"));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        return productRepository.findAll(spec).stream().map(ProductDTO::fromEntity).collect(Collectors.toList());
    }

    public List<ProductDTO> searchProducts(Category category, CategoryLow categoryLow, Gender gender) {
        Specification<Product> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (category != null) {
                predicates.add(criteriaBuilder.equal(root.get("category"), category));
            }
            if (categoryLow != null) {
                predicates.add(criteriaBuilder.equal(root.get("categoryLow"), categoryLow));
            }
            if (gender != null) {
                predicates.add(criteriaBuilder.equal(root.get("gender"), gender));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        return productRepository.findAll(spec).stream().map(ProductDTO::fromEntity).collect(Collectors.toList());
    }

    public List<ProductDTO> searchProductsByUserNum(Long userNum) {
        User user = userRepository.findById(userNum)
                .orElseThrow(() -> new EntityNotFoundException("User", userNum));
        return productRepository.findByUser(user).stream().map(ProductDTO::fromEntity).collect(Collectors.toList());
    }

    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream().map(ProductDTO::fromEntity).collect(Collectors.toList());
    }


    public List<ProductDTO> getAllProductsByUser(Long userId) {
        return productRepository.findByUserUserNum(userId).stream().map(ProductDTO::fromEntity).toList();
    }

    public List<ProductSimpleDTO.Response> getAllProducts2() {
        return productRepository.findAll().stream().map(ProductSimpleDTO.Response::fromEntity).collect(Collectors.toList());
    }

    public ProductSimpleDTO.ProductDetailResponse getProductById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));
        return ProductSimpleDTO.ProductDetailResponse.fromEntity(product);
    }
}