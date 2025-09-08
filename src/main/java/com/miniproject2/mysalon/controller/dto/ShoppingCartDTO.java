package com.miniproject2.mysalon.controller.dto;

import com.miniproject2.mysalon.entity.ShoppingCart;
import jakarta.validation.constraints.NotNull;
import lombok.*;

public class ShoppingCartDTO {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Request {

        @NotNull(message = "사용자 번호는 필수 입력 항목입니다.")
        private Long userNum;

        @NotNull(message = "상품 상세 번호는 필수 입력 항목입니다.")
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
        private int count;
        private boolean isSelected;

        public static Response fromEntity(ShoppingCart cart) {
            return Response.builder()
                    .userNum(cart.getUser().getUserNum())
                    .productDetailNum(cart.getProductDetail().getProductDetailNum())
                    .count(cart.getCount())
                    .isSelected(cart.isSelected())
                    .build();
        }
    }
}
