package com.miniproject2.mysalon.repository;

import com.miniproject2.mysalon.entity.ProductDetail;
import com.miniproject2.mysalon.entity.Review;
import com.miniproject2.mysalon.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByUser(User user);

    List<Review> findByProductDetail(ProductDetail productDetail);

    List<Review> findByUserAndProductDetail(User user, ProductDetail productDetail);
}