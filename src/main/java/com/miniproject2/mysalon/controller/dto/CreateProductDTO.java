package com.miniproject2.mysalon.controller.dto;

import com.miniproject2.mysalon.entity.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CreateProductDTO {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ProductRequest {

        @NotBlank(message = "상품 이름은 필수입니다.")
        private String productName;
        @NotBlank(message = "가격은 필수입니다.")
        private Long price;
        @NotBlank(message = "배송비는 필수입니다.")
        private Long deliveryFee;
        private String description;
        private Gender gender;
        private Category category;
        private CategoryLow categoryLow;
        private List<ProductDetailDTO2> productDetails;

        // toEntity 메서드는 파일 이름(mainImage)을 매개변수로 받아 엔티티에 설정합니다.
        public  Product toEntity(User user, String mainImage) {
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
                    .user(user)
                    .productName(this.productName)
                    .price(this.price)
                    .deliveryPrice(this.deliveryFee)
                    .mainImage(mainImage)
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
