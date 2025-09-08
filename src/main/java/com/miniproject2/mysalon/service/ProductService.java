package com.miniproject2.mysalon.service;

import com.miniproject2.mysalon.controller.dto.ProductDTO;
import com.miniproject2.mysalon.entity.Product;
import com.miniproject2.mysalon.entity.ProductDetail;
import com.miniproject2.mysalon.repository.ProductDetailRepository;
import com.miniproject2.mysalon.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductDetailRepository productDetailRepository;

    @Transactional
    public Long createProduct(ProductDTO.Request request) {
        Product product = Product.builder()
                .productName(request.getProductName())
                .price(request.getPrice())
                .mainImage(request.getMainImage())
                .description(request.getDescription())
                .gender(request.getGender())
                .category(request.getCategory())
                .build();

        Product savedProduct = productRepository.save(product);

        if (request.getProductDetails() != null) {
            for (ProductDTO.ProductDetailDTO detailDTO : request.getProductDetails()) {
                ProductDetail productDetail = ProductDetail.builder()
                        .product(savedProduct)
                        .size(detailDTO.getSize())
                        .color(detailDTO.getColor())
                        .count(detailDTO.getCount())
                        .image(detailDTO.getImage())
                        .build();
                productDetailRepository.save(productDetail);
            }
        }

        return savedProduct.getProductNum();
    }

    @Transactional(readOnly = true)
    public ProductDTO.Response getProductById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        List<ProductDetail> productDetails = productDetailRepository.findByProduct(product);

        return ProductDTO.Response.fromEntity(product, productDetails);
    }

    // Add methods for update and delete
}