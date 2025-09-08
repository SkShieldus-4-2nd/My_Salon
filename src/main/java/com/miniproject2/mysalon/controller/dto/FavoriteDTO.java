package com.miniproject2.mysalon.controller.dto;

import com.miniproject2.mysalon.entity.Favorite;
import jakarta.validation.constraints.NotNull;
import lombok.*;

public class FavoriteDTO {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Request {

        @NotNull(message = "사용자 번호는 필수 입력 항목입니다.")
        private Long userNum;

        @NotNull(message = "상품 번호는 필수 입력 항목입니다.")
        private Long productNum;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private Long userNum;
        private Long productNum;

        public static Response fromEntity(Favorite favorite) {
            return Response.builder()
                    .userNum(favorite.getUser().getUserNum())
                    .productNum(favorite.getProduct().getProductNum())
                    .build();
        }
    }
}
