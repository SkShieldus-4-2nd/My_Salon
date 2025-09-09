package com.miniproject2.mysalon.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDetailDTO {
    private Long productDetailNum;
    private String size;
    private String color;
    private Integer count;
    private String image;
}
