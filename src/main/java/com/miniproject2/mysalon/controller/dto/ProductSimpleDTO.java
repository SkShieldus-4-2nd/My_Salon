package com.miniproject2.mysalon.controller.dto;

import com.miniproject2.mysalon.entity.*;
import lombok.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ProductSimpleDTO {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private Long productNum;
        private String productName;
        private Long price;
        private String mainImage;

        // Entity -> DTO
        public static ProductSimpleDTO.Response fromEntity(Product product) {
            return ProductSimpleDTO.Response.builder()
                    .productNum(product.getProductNum())
                    .productName(product.getProductName())
                    .price(product.getPrice())
                    .mainImage(product.getMainImage())
                    .build();
        }

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ProductDetailResponse {
        private Long productNum;
        private String productName;
        private Long price;
        private Long deliveryFee;
        private String mainImage;
        private String description;
        private List<ProductDetailDTO> productDetails;

        // Entity -> DTO
        public static ProductDetailResponse fromEntity(Product product) {
            return ProductDetailResponse.builder()
                    .deliveryFee(product.getDeliveryPrice())
                    .productNum(product.getProductNum())
                    .productName(product.getProductName())
                    .price(product.getPrice())
                    .mainImage(product.getMainImage())
                    .description(product.getDescription())
                    .productDetails(product.getProductDetails() != null ? product.getProductDetails().stream()
                            .map(ProductDetailDTO::fromEntity)
                            .collect(Collectors.toList()) : Collections.emptyList())
                    .build();
        }

    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ProductDetailDTO {

        private int count;
        private String color;
        private String size;


        public static ProductDetailDTO fromEntity(ProductDetail entity){

            return ProductDetailDTO.builder()
                    .color(entity.getColor())
                    .size(entity.getSize())
                    .count(entity.getCount())
                    .build();

        }


    }
    }
