package com.miniproject2.mysalon.controller.dto;

import com.miniproject2.mysalon.entity.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {
    private Long productNum;
    private Long userNum;
    private String productName;
    private Long price;
    private Long deliveryFee;
    private String mainImage;
    private String description;
    private Gender gender;
    private Category category;
    private CategoryLow categoryLow;
    private List<ProductDetailDTO> productDetails;

    // Entity -> DTO
    public static ProductDTO fromEntity(Product product) {
        return ProductDTO.builder()
                .deliveryFee(product.getDeliveryPrice())
                .productNum(product.getProductNum())
                .userNum(product.getUser().getUserNum())
                .productName(product.getProductName())
                .price(product.getPrice())
                .mainImage(product.getMainImage())
                .description(product.getDescription())
                .gender(product.getGender())
                .category(product.getCategory())
                .categoryLow(product.getCategoryLow())
                .productDetails(product.getProductDetails() != null ? product.getProductDetails().stream()
                        .map(ProductDetailDTO::fromEntity)
                        .collect(Collectors.toList()) : Collections.emptyList())
                .build();
    }

    // DTO -> Entity
    public Product toEntity(User user) {
        Product product = Product.builder()
                .deliveryPrice(this.deliveryFee)
                .productNum(this.productNum)
                .user(user)
                .productName(this.productName)
                .price(this.price)
                .mainImage(this.mainImage)
                .description(this.description)
                .gender(this.gender)
                .category(this.category)
                .categoryLow(this.categoryLow)
                .build();

        if (this.productDetails != null) {
            List<ProductDetail> details = this.productDetails.stream()
                    .map(detailDTO -> detailDTO.toEntity(product))
                    .collect(Collectors.toList());
            product.setProductDetails(details);
        }
        return product;
    }
}
