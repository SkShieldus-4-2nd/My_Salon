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

    private int quantity;

    public ShoppingCart() {}

    // getter, setter
}
