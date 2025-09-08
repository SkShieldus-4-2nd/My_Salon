package com.miniproject2.mysalon.repository;

import com.miniproject2.mysalon.entity.Favorite;
import com.miniproject2.mysalon.entity.FavoriteId;
import com.miniproject2.mysalon.entity.Product;
import com.miniproject2.mysalon.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, FavoriteId> {

    List<Favorite> findByUser(User user);

    List<Favorite> findByProduct(Product product);
}
