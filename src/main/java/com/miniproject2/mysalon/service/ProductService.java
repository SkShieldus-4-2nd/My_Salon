package com.miniproject2.mysalon.service;

import com.miniproject2.mysalon.controller.dto.ProductDTO;
import com.miniproject2.mysalon.entity.*;
import com.miniproject2.mysalon.exception.EntityNotFoundException;
import com.miniproject2.mysalon.repository.ProductDetailRepository;
import com.miniproject2.mysalon.repository.ProductRepository;
import com.miniproject2.mysalon.repository.UserRepository;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductDetailRepository productDetailRepository;
    private final UserRepository userRepository;

    public ProductDTO createProduct(ProductDTO productDTO) {
        User user = userRepository.findById(productDTO.getUserNum())
                .orElseThrow(() -> new EntityNotFoundException("User", productDTO.getUserNum()));
        Product product = productDTO.toEntity(user);
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

    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product", productId));
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
}