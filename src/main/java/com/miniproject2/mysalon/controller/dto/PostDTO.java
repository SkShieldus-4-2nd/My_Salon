package com.miniproject2.mysalon.controller.dto;

import com.miniproject2.mysalon.entity.Post;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

public class PostDTO {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PostRequest {

        @NotNull(message = "작성자 번호는 필수 입력 항목입니다.")
        private Long userNum;

        @NotBlank(message = "제목은 필수 입력 항목입니다.")
        private String title;

        @NotBlank(message = "본문 내용은 필수 입력 항목입니다.")
        private String text;

        private String image;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {

        private String title;
        private String text;
        private Long likeCount;
        private String image;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public static Response fromEntity(Post post) {
            return Response.builder()
                    .title(post.getTitle())
                    .text(post.getText())
                    .likeCount(post.getLikeCount())
                    .image(post.getImage())
                    .createdAt(post.getCreatedAt())
                    .updatedAt(post.getUpdatedAt())
                    .build();
        }
    }

}
