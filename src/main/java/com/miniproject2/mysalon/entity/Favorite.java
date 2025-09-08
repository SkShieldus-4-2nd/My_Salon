package com.miniproject2.mysalon.entity;

import com.miniproject2.mysalon.entity.FavoriteId;
import com.miniproject2.mysalon.entity.Product;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "favorites")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Favorite {

    @EmbeddedId
    private FavoriteId id;

    @MapsId("userNum") // FavoriteId.userNum 매핑
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_num", nullable = false)
    private User user;

    @MapsId("productNum") // FavoriteId.productNum 매핑
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_num", nullable = false)
    private Product product;
}
