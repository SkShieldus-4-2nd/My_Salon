package com.miniproject2.mysalon.controller.dto;

import com.miniproject2.mysalon.entity.Comment;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

public class CommentDTO {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Request {

        @NotNull(message = "작성자 번호는 필수 입력 항목입니다.")
        private Long userNum;

        @NotNull(message = "게시글 번호는 필수 입력 항목입니다.")
        private Long postNum;

        @NotBlank(message = "댓글 내용은 필수 입력 항목입니다.")
        private String text;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private Long commentNum;
        private Long userNum;
        private Long postNum;
        private String text;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public static Response fromEntity(Comment comment) {
            return Response.builder()
                    .commentNum(comment.getCommentNum())
                    .userNum(comment.getUser().getUserNum())
                    .postNum(comment.getPost().getPostNum())
                    .text(comment.getText())
                    .createdAt(comment.getCreatedAt())
                    .updatedAt(comment.getUpdatedAt())
                    .build();
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CommentResponse {

        private String userName;
        private String profile;
        private String text;
        private LocalDateTime updatedAt;

        public static CommentResponse fromEntity(Comment comment) {
            return CommentResponse.builder()
                    .userName(comment.getUser().getUserName())
                    .text(comment.getText())
                    .updatedAt(comment.getUpdatedAt())
                    .profile(comment.getUser().getProfileImage())
                    .build();
        }
    }
}
