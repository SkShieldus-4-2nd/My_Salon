package com.miniproject2.mysalon.repository;

import com.miniproject2.mysalon.entity.ShoppingCart;
import com.miniproject2.mysalon.entity.ShoppingCartKey;
import com.miniproject2.mysalon.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, ShoppingCartKey> {

    List<ShoppingCart> findByUser(User user);
}
