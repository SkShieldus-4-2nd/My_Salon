package com.miniproject2.mysalon.controller.dto;

import com.miniproject2.mysalon.entity.ShoppingCart;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

public class ShoppingCartDTO {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CartRequest {


        @NotNull(message = "상품 상세 번호는 필수 입력 항목입니다.")
        private Long productDetailNum;
        @Positive(message = "상품 수량은 양수여야 합니다.")
        private int count;

        private boolean isSelected;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private Long userNum;
        private Long productDetailNum;
        private String productName;
        private String productImage;
        private Long productPrice;
        private String size;
        private String color;
        private int count;
        private boolean isSelected;

        public static Response fromEntity(ShoppingCart cart) {
            return Response.builder()
                    .userNum(cart.getUser().getUserNum())
                    .productDetailNum(cart.getProductDetail().getProductDetailNum())
                    .productName(cart.getProductDetail().getProduct().getProductName())
                    .productImage(cart.getProductDetail().getProduct().getMainImage())
                    .productPrice(cart.getProductDetail().getProduct().getPrice())
                    .size(cart.getProductDetail().getSize())
                    .color(cart.getProductDetail().getColor())
                    .count(cart.getCount())
                    .isSelected(cart.isSelected())
                    .build();
        }
    }

}
