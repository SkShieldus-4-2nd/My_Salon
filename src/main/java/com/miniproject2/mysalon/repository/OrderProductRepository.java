package com.miniproject2.mysalon.repository;

import com.miniproject2.mysalon.entity.Order;
import com.miniproject2.mysalon.entity.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {

    List<OrderProduct> findByOrder(Order order);
}
