package com.miniproject2.mysalon.controller.dto;

import com.miniproject2.mysalon.entity.Category;
import com.miniproject2.mysalon.entity.CategoryLow;
import com.miniproject2.mysalon.entity.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {
    private Long productNum;
    private Long userNum;
    private String productName;
    private Long price;
    private String mainImage;
    private String description;
    private Gender gender;
    private Category category;
    private CategoryLow categoryLow;
}
