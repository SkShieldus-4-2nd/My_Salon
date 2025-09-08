package com.miniproject2.mysalon.controller.dto;

import com.miniproject2.mysalon.entity.Category;
import com.miniproject2.mysalon.entity.Gender;
import com.miniproject2.mysalon.entity.Product;
import com.miniproject2.mysalon.entity.ProductDetail;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

public class ProductDTO {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Request {
        @NotBlank(message = "상품이름은 필수 입력 항목입니다.")
        private String productName;

        @NotNull(message = "가격은 필수 입력 항목입니다.")
        private Long price;

        @NotBlank(message = "메인이미지는 필수 입력 항목입니다.")
        private String mainImage;

        @NotBlank(message = "설명은 필수 입력 항목입니다.")
        private String description;

        @NotNull(message = "성별/나이유형은 필수 입력 항목입니다.")
        private Gender gender;

        @NotNull(message = "카테고리는 필수 입력 항목입니다.")
        private Category category;
        private List<ProductDetailDTO> productDetails;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private Long productNum;
        private String productName;
        private Long price;
        private String mainImage;
        private String description;
        private Gender gender;
        private Category category;
        private List<ProductDetailDTO> productDetails;

        public static Response fromEntity(Product product, List<ProductDetail> productDetails) {
            return Response.builder()
                    .productNum(product.getProductNum())
                    .productName(product.getProductName())
                    .price(product.getPrice())
                    .mainImage(product.getMainImage())
                    .description(product.getDescription())
                    .gender(product.getGender())
                    .category(product.getCategory())
                    .productDetails(productDetails.stream().map(productDetail -> ProductDetailDTO.builder()
                            .productDetailNum(productDetail.getProductDetailNum())
                            .size(productDetail.getSize())
                            .color(productDetail.getColor())
                            .count(productDetail.getCount())
                            .image(productDetail.getImage())
                            .build()).collect(Collectors.toList()))
                    .build();
        }
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ProductDetailDTO {
        private Long productDetailNum;
        private String size;
        private String color;
        private Integer count;
        private String image;
    }
}