package com.miniproject2.mysalon.entity;

import com.miniproject2.mysalon.entity.ProductDetail;
import com.miniproject2.mysalon.entity.ShoppingCartKey;
import jakarta.persistence.*;

@Entity
@Table(name = "shopping_cart")
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
