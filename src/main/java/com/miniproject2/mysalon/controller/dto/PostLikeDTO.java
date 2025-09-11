package com.miniproject2.mysalon.controller.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class PostLikeDTO {


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ClickRequest {

        @NotNull(message = "게시물 번호는 필수 입력 항목입니다.")
        private Long postNum;

    }
}
