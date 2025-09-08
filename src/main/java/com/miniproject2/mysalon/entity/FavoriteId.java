package com.miniproject2.mysalon.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteId implements Serializable {
    private Long userNum;
    private Long productNum;

    // 반드시 equals & hashCode 구현해야 함
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FavoriteId)) return false;
        FavoriteId that = (FavoriteId) o;
        return Objects.equals(userNum, that.userNum) &&
                Objects.equals(productNum, that.productNum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userNum, productNum);
    }
}
