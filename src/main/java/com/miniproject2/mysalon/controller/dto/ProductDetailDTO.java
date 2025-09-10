package com.miniproject2.mysalon.controller.dto;

import com.miniproject2.mysalon.entity.Product;
import com.miniproject2.mysalon.entity.ProductDetail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import lombok.Builder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDetailDTO {
    private Long productDetailNum;
    private Long productNum;
    private String color;
    private String size;
    private int count; // stock -> count
    private String image;

    public static ProductDetailDTO fromEntity(ProductDetail entity) {
        return new ProductDetailDTO(
                entity.getProductDetailNum(),
                entity.getProduct().getProductNum(),
                entity.getColor(),
                entity.getSize(),
                entity.getCount(), // getStock() -> getCount()
                entity.getImage()
        );
    }

    public ProductDetail toEntity(Product product) {
        return new ProductDetail(
                this.productDetailNum,
                this.size,
                this.color,
                this.count, // stock -> count
                this.image,
                product
        );
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateRequest {
        private String color;
        private String size;
        private int count;
        private String image;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PatchRequest {
        private String color;
        private String size;
        private Integer count;
        private String image;
    }
}
