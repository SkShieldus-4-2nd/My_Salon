package com.miniproject2.mysalon.controller.dto;

import com.miniproject2.mysalon.entity.Post;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

public class PostDTO {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PostRequest {


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
        private Long postId;
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

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SimplePost {
        private String image;
        private String title;
        private String writer;
        private LocalDateTime writingDate;
        private Integer commentCount;


        public static SimplePost fromEntity(Post post) {
            return SimplePost.builder()
                    .image(post.getImage())
                    .title(post.getTitle())
                    .writer(post.getUser().getUserName())
                    .writingDate(post.getUpdatedAt())
                    .commentCount(post.getComments().size())
                    .build();
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SimpleCoordiPost {
        private String coordiImage;
        private String title;
        private String writer;
        private String userImage;
        private Long likeCount;
        private boolean isLiked;


    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PostDetail {
        private String postImage;
        private String title;
        private String writer;
        private String text;
        List<CommentDTO.CommentResponse> comments;

        public static PostDetail fromEntity(Post post) {
            return PostDetail.builder()
                    .postImage(post.getImage())
                    .title(post.getTitle())
                    .writer(post.getUser().getUserName())
                    .text(post.getText())
                    .comments(post.getComments().stream().map(CommentDTO.CommentResponse::fromEntity).toList())
                    .build();
        }
    }





}
