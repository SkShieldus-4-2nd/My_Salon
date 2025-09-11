package com.miniproject2.mysalon.controller.dto;

import com.miniproject2.mysalon.entity.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CreateProductDTO {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ProductRequest {
        @Positive
        private Long userNum;
        @NotBlank(message = "상품 이름은 필수입니다.")
        private String productName;
        @NotBlank(message = "가격은 필수입니다.")
        private Long price;
        @NotBlank(message = "배송비는 필수입니다.")
        private Long deliveryFee;
        private String mainImage;
        private String description;
        private Gender gender;
        private Category category;
        private CategoryLow categoryLow;
        private List<ProductDetailDTO2> productDetails;


        public static ProductDTO fromEntity(Product product, User user) {
            return ProductDTO.builder()

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

            if (this.category == null) {
                this.category = Category.ALL;
            }
            if (this.categoryLow == null) {
                this.categoryLow = CategoryLow.ALL;
            }
            if (this.gender == null) {
                this.gender = Gender.ALL;
            }
            Product product = Product.builder()
                    .deliveryPrice(this.deliveryFee)
                    .user(user)
                    .productName(this.productName)
                    .price(this.price)
                    .mainImage(this.mainImage)
                    .description(this.description)
                    .gender(this.gender)
                    .likeCount(0L)
                    .category(this.category)
                    .categoryLow(this.categoryLow)
                    .build();

            if (this.productDetails != null) {
                List<ProductDetail> details = this.productDetails.stream()
                        .map(detailDTO -> detailDTO.toEntity(detailDTO, product))
                        .collect(Collectors.toList());
                product.setProductDetails(details);
            }
            return product;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ProductDetailDTO2 {

        private String color;
        private String size;
        private int count;


        public static ProductDetailDTO2 fromEntity(ProductDetail entity) {
            return new ProductDetailDTO2(
                    entity.getColor(),
                    entity.getSize(),
                    entity.getCount() // getStock() -> getCount()

            );
        }

        public ProductDetail toEntity(ProductDetailDTO2 dto, Product product) {
            return ProductDetail.builder()
                    .color(dto.getColor())
                    .size(dto.getSize())
                    .count(dto.getCount())
                    .product(product)
                    .build();
        }
    }



}
