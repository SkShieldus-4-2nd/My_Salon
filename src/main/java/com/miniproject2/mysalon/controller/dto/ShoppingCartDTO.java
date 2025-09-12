package com.miniproject2.mysalon.controller.dto;

import com.miniproject2.mysalon.entity.ShoppingCart;
import lombok.*;

public class ShoppingCartDTO {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Request {
        private Long userNum;
        private Long productDetailNum;
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
                    .productPrice(cart.getProductDetail().getProduct().getPrice())
                    .size(cart.getProductDetail().getSize())
                    .color(cart.getProductDetail().getColor())
                    .count(cart.getCount())
                    .isSelected(cart.isSelected())
                    .build();
        }
    }

}
