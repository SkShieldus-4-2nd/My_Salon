package com.miniproject2.mysalon.controller.dto;

import com.miniproject2.mysalon.entity.User;
import com.miniproject2.mysalon.entity.UserType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDateTime;

public class UserDTO {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Request {

        @NotBlank(message = "아이디는 필수 입력 항목입니다.")
        private String id;

        @NotBlank(message = "비밀번호는 필수 입력 항목입니다.")
        private String password;

        @NotBlank(message = "닉네임은 필수 입력 항목입니다.")
        private String userName;

        @NotBlank(message = "2차 비밀번호는 필수 입력 항목입니다.")
        @Pattern(regexp = "^[0-9]{6}$", message = "2차 비밀번호는 6자리 숫자여야 합니다.")
        private String secondPassword;

        private String profileImage;
        private Short tall;
        private Short weight;
        private LocalDateTime createdAt;
        private UserType type;   // SELLER, BUYER, ADMIN
        private String storeName;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private Long userNum;
        private String id;
        private String userName;
        private String secondPassword;
        private String profileImage;
        private Short tall;
        private Short weight;
        private LocalDateTime createdAt;
        private UserType type;
        private String storeName;

        public static Response fromEntity(User user) {
            return Response.builder()
                    .userNum(user.getUserNum())
                    .id(user.getId())
                    .userName(user.getUserName())
                    .secondPassword(user.getSecondPassword())
                    .profileImage(user.getProfileImage())
                    .tall(user.getTall())
                    .weight(user.getWeight())
                    .createdAt(user.getCreatedAt())
                    .type(user.getType())
                    .storeName(user.getStoreName())
                    .build();
        }
    }
}