package com.miniproject2.mysalon.repository;

import com.miniproject2.mysalon.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
}
