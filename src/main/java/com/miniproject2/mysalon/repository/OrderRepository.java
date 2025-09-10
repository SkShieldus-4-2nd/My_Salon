package com.miniproject2.mysalon.repository;

import com.miniproject2.mysalon.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUser_UserNum(Long userNum);

    @Query("SELECT o FROM Order o JOIN o.orderProducts op JOIN op.productDetail pd JOIN pd.product p WHERE p.productNum = :productNum")
    List<Order> findByProductNum(@Param("productNum") Long productNum);

    @Query("SELECT o FROM Order o JOIN o.orderProducts op JOIN op.productDetail pd JOIN pd.product p WHERE " +
            "(:keyword1 IS NULL OR p.productName LIKE %:keyword1%) AND " +
            "(:keyword2 IS NULL OR p.productName LIKE %:keyword2%) AND " +
            "(:keyword3 IS NULL OR p.productName LIKE %:keyword3%)")
    List<Order> findByProductNameKeywords(@Param("keyword1") String keyword1, @Param("keyword2") String keyword2, @Param("keyword3") String keyword3);

}
