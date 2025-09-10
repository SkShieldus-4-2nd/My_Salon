package com.miniproject2.mysalon.repository;

import com.miniproject2.mysalon.entity.Favorite;
import com.miniproject2.mysalon.entity.FavoriteId;
import com.miniproject2.mysalon.entity.Product;
import com.miniproject2.mysalon.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, FavoriteId> {

    List<Favorite> findByUser(User user);

    List<Favorite> findByProduct(Product product);

    // 유저가 찜한 상품 개수
    @Query("SELECT COUNT(f) FROM Favorite f WHERE f.user = :user")
    Long countByUser(@Param("user") User user);

    // 특정 상품을 찜한 유저 수
    @Query("SELECT COUNT(f) FROM Favorite f WHERE f.product.productNum = :productNum")
    Long countByProductNum(@Param("productNum") Long productNum);
}
