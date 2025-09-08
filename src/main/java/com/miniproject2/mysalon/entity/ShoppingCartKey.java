package com.miniproject2.mysalon.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
public class ShoppingCartKey implements Serializable {

    private Long userNum;
    private Long productDetailNum;

    public ShoppingCartKey() {}

    public ShoppingCartKey(Long userNum, Long productDetailNum) {
        this.userNum = userNum;
        this.productDetailNum = productDetailNum;
    }

    // getter, setter

    // equals, hashCode 반드시 오버라이드
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ShoppingCartKey)) return false;
        ShoppingCartKey that = (ShoppingCartKey) o;
        return userNum.equals(that.userNum) && productDetailNum.equals(that.productDetailNum);
    }

    @Override
    public int hashCode() {
        return userNum.hashCode() + productDetailNum.hashCode();
    }
}
