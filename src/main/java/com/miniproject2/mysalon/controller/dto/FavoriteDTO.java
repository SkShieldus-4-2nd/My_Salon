package com.miniproject2.mysalon.controller.dto;

import com.miniproject2.mysalon.entity.Category;
import com.miniproject2.mysalon.entity.Favorite;
import com.miniproject2.mysalon.entity.Product;
import jakarta.validation.constraints.NotNull;
import lombok.*;

public class FavoriteDTO {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class FavoriteRequest {

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
        private String productName;
        private String productImage;
        private Long productPrice;
        private Category category;
        public static Response fromEntity(Favorite favorite) {
            return Response.builder()
                    .userNum(favorite.getUser().getUserNum())
                    .productNum(favorite.getProduct().getProductNum())
                    .productName(favorite.getProduct().getProductName())
                    .productImage(favorite.getProduct().getMainImage())
                    .productPrice(favorite.getProduct().getPrice())
                    .category(favorite.getProduct().getCategory())
                    .build();
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ClickResponse {
        private Long productNum;
        private Long likeCount;

        public static ClickResponse fromEntity(Product product) {
            return ClickResponse.builder()
                    .likeCount(product.getLikeCount())
                    .productNum(product.getProductNum())
                    .build();
        }
    }
}
