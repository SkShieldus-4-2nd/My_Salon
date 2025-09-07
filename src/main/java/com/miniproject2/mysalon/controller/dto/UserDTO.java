package com.miniproject2.mysalon.controller.dto;

import com.miniproject2.mysalon.entity.User;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

public class UserDTO {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Request {

        @NotBlank(message = "아이디는 필수 입력 항목입니다.")
        private String id;

        @NotBlank(message = "Name은 필수 입력 항목입니다.")
        private String userName;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private Long userNum;
        private String id;
        private String userName;

        public static Response fromEntity(User user) {
            return Response.builder()
                    .userNum(user.getUserNum())
                    .id(user.getId())
                    .userName(user.getUserName())
                    .build();

        }
    }

}
