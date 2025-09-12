package com.miniproject2.mysalon.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "shopping_cart")
@Getter
@Setter
public class ShoppingCart {

    @EmbeddedId
    private ShoppingCartKey id;

    @ManyToOne
    @MapsId("userNum") // EmbeddedId의 userNum 매핑
    @JoinColumn(name = "user_num")
    private User user;

    @ManyToOne
    @MapsId("productDetailNum") // EmbeddedId의 productDetailNum 매핑
    @JoinColumn(name = "product_detail_num")
    private ProductDetail productDetail;

    @Column
    private int count;

    @Column(nullable = false)
    private boolean isSelected = true;

    // 새로 추가
    private String productName;


    private int productPrice;


    private String size;


    private String color;

}
