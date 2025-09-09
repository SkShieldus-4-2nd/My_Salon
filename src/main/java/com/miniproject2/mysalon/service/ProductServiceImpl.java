package com.miniproject2.mysalon.service;

import com.miniproject2.mysalon.controller.dto.ProductDTO;
import com.miniproject2.mysalon.entity.*;
import com.miniproject2.mysalon.exception.EntityNotFoundException;
import com.miniproject2.mysalon.repository.ProductRepository;
import com.miniproject2.mysalon.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public Product createProduct(ProductDTO productDTO) {
        User user = userRepository.findById(productDTO.getUserNum())
                .orElseThrow(() -> new EntityNotFoundException("User", productDTO.getUserNum()));

        Product product = Product.builder()
                .userNum(user)
                .productName(productDTO.getProductName())
                .price(productDTO.getPrice())
                .mainImage(productDTO.getMainImage())
                .description(productDTO.getDescription())
                .gender(productDTO.getGender())
                .category(productDTO.getCategory())
                .categoryLow(productDTO.getCategoryLow())
                .build();

        return productRepository.save(product);
    }

    @Override
    @Transactional
    public Product editProduct(Long productId, ProductDTO productDTO) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product", productId));

        // Patch-style update: only update non-null fields from DTO
        if (productDTO.getProductName() != null) {
            product.setProductName(productDTO.getProductName());
        }
        if (productDTO.getPrice() != null) {
            product.setPrice(productDTO.getPrice());
        }
        if (productDTO.getMainImage() != null) {
            product.setMainImage(productDTO.getMainImage());
        }
        if (productDTO.getDescription() != null) {
            product.setDescription(productDTO.getDescription());
        }
        if (productDTO.getGender() != null) {
            product.setGender(productDTO.getGender());
        }
        if (productDTO.getCategory() != null) {
            product.setCategory(productDTO.getCategory());
        }
        if (productDTO.getCategoryLow() != null) {
            product.setCategoryLow(productDTO.getCategoryLow());
        }

        return productRepository.save(product);
    }

    @Override
    @Transactional
    public void deleteProduct(Long productId) {
        if (!productRepository.existsById(productId)) {
            throw new EntityNotFoundException("Product", productId);
        }
        productRepository.deleteById(productId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> searchProductByProductName(String keyword) {
        String[] keywords = keyword.split("\\s+");
        Specification<Product> spec = (root, query, criteriaBuilder) -> {
            Predicate[] predicates = new Predicate[keywords.length];
            for (int i = 0; i < keywords.length; i++) {
                predicates[i] = criteriaBuilder.like(criteriaBuilder.lower(root.get("productName")), "%" + keywords[i].toLowerCase() + "%");
            }
            return criteriaBuilder.and(predicates);
        };
        return productRepository.findAll(spec);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> searchProductsByCategoryAndCategoryLow(Category category, CategoryLow categoryLow) {
        if (category != null && categoryLow != null) {
            return productRepository.findByCategoryAndCategoryLow(category, categoryLow);
        } else if (category != null) {
            return productRepository.findByCategory(category);
        } else {
            return productRepository.findAll();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> searchProductsByUserNum(Long userNum) {
        User user = userRepository.findById(userNum)
                .orElseThrow(() -> new EntityNotFoundException("User", userNum));
        return productRepository.findByUserNum(user);
    }
}
