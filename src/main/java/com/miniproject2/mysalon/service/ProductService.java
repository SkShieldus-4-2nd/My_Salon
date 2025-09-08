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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductDetailRepository productDetailRepository;

    @Transactional
    public Product createProduct(ProductDTO productDTO) {
        Product product = Product.builder()
                .productName(productDTO.getProductName())
                .price(productDTO.getPrice())
                .mainImage(productDTO.getMainImage())
                .description(productDTO.getDescription())
                .gender(productDTO.getGender())
                .category(productDTO.getCategory())
                .build();

        Product savedProduct = productRepository.save(product);

        if (productDTO.getProductDetails() != null) {
            for (ProductDTO.ProductDetailDTO detailDTO : productDTO.getProductDetails()) {
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

        return savedProduct;
    }

    @Transactional(readOnly = true)
    public ProductDTO getProductById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        List<ProductDetail> productDetails = productDetailRepository.findByProduct(product);

        List<ProductDTO.ProductDetailDTO> detailDTOs = productDetails.stream()
                .map(detail -> ProductDTO.ProductDetailDTO.builder()
                        .productDetailNum(detail.getProductDetailNum())
                        .size(detail.getSize())
                        .color(detail.getColor())
                        .count(detail.getCount())
                        .image(detail.getImage())
                        .build())
                .collect(Collectors.toList());

        return ProductDTO.builder()
                .productNum(product.getProductNum())
                .productName(product.getProductName())
                .price(product.getPrice())
                .mainImage(product.getMainImage())
                .description(product.getDescription())
                .gender(product.getGender())
                .category(product.getCategory())
                .productDetails(detailDTOs)
                .build();
    }

    // Add methods for update and delete
}