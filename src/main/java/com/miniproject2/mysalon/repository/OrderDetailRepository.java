package com.miniproject2.mysalon.repository;

import com.miniproject2.mysalon.entity.OrderDetail;
import com.miniproject2.mysalon.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

}
