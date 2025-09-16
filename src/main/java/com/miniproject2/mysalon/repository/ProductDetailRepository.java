package com.miniproject2.mysalon.repository;

import com.miniproject2.mysalon.entity.OrderStatus;
import com.miniproject2.mysalon.entity.Product;
import com.miniproject2.mysalon.entity.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductDetailRepository extends JpaRepository<ProductDetail, Long> {

    List<ProductDetail> findByProduct(Product product);

    List<ProductDetail> findByProduct_ProductNum(Long productNum);

    List<ProductDetail> findByProduct_User_UserNum(Long userNum);

    List<ProductDetail> findByColor(String color);

    List<ProductDetail> findByColorAndSize(String color, String size);

    Optional<ProductDetail> findByProduct_ProductNumAndSizeAndColor(Long productNum, String size, String color);

    List<ProductDetail> findByOrderDetail_OrderStatusAndProduct_User_UserNum(OrderStatus orderStatus,Long userNum);
}
