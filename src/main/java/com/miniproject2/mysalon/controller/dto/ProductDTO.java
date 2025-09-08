package com.miniproject2.mysalon.controller.dto;

import com.miniproject2.mysalon.entity.Category;
import com.miniproject2.mysalon.entity.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {

    private Long productNum;
    private String productName;
    private Long price;
    private String mainImage;
    private String description;
    private Gender gender;
    private Category category;
    private List<ProductDetailDTO> productDetails;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ProductDetailDTO {
        private Long productDetailNum;
        private String size;
        private String color;
        private Integer count;
        private String image;
    }
}