package com.miniproject2.mysalon.controller.dto;

import com.miniproject2.mysalon.entity.Review;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class ReviewDTO {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Request {

        @NotNull(message = "회원 정보는 필수입니다.")
        private Long userNum;

        @NotNull(message = "상품 상세 정보는 필수입니다.")
        private Long productDetailNum;

        @NotBlank(message = "리뷰 내용은 필수입니다.")
        private String text;

        @NotNull(message = "평점은 필수입니다.")
        @Min(value = 1, message = "평점은 1에서 5 사이여야 합니다.")
        @Max(value = 5, message = "평점은 1에서 5 사이여야 합니다.")
        private Short score;

        private String reviewImage;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private Long reviewNum;
        private String userName;
        private Long productDetailNum;
        private String text;
        private Short score;
        private String reviewImage;

        public static Response fromEntity(Review review) {
            return Response.builder()
                    .reviewNum(review.getReviewNum())
                    .userName(review.getUser().getUserName())
                    .productDetailNum(review.getProductDetail().getProductDetailNum())
                    .text(review.getText())
                    .score(review.getScore())
                    .reviewImage(review.getReviewImage())
                    .build();
        }
    }
}