package com.miniproject2.mysalon.repository;

import com.miniproject2.mysalon.entity.ProductDetail;
import com.miniproject2.mysalon.entity.Review;
import com.miniproject2.mysalon.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByUser(User user);

    List<Review> findByProductDetail(ProductDetail productDetail);

    List<Review> findByUserAndProductDetail(User user, ProductDetail productDetail);

    List<Review> findByProductDetail_ProductDetailNum(Long productDetailNum);

    @Query("SELECT r FROM Review r WHERE r.productDetail.product.productNum = :productNum")
    List<Review> findByProductNum(@Param("productNum") Long productNum);

    @Query("SELECT r FROM Review r WHERE r.user = :user AND r.productDetail.product.productNum = :productNum")
    List<Review> findByUserAndProductNum(@Param("user") User user, @Param("productNum") Long productNum);

    // productNum 기준 리뷰 score 평균
    @Query("SELECT AVG(r.score) FROM Review r WHERE r.productDetail.product.productNum = :productNum")
    Double findAverageScoreByProductNum(@Param("productNum") Long productNum);

    // 1. 유저 본인이 작성한 리뷰 개수
    @Query("SELECT COUNT(r) FROM Review r WHERE r.user = :user")
    Long countByUser(@Param("user") User user);

    // 2. 특정 상품(Product) 리뷰 개수
    @Query("SELECT COUNT(r) FROM Review r WHERE r.productDetail.product.productNum = :productNum")
    Long countByProductNum(@Param("productNum") Long productNum);

    //최신순 로드
    @Query("SELECT r FROM Review r WHERE r.productDetail.product.productNum = :productNum ORDER BY r.createdAt DESC")
    List<Review> findByProductNumOrderByCreatedAtDesc(@Param("productNum") Long productNum);


    // 별점 높은 순, 동일 별점 내에서는 최신순
    @Query("SELECT r FROM Review r " +
            "WHERE r.productDetail.product.productNum = :productNum " +
            "ORDER BY r.score DESC, r.createdAt DESC")
    List<Review> findByProductNumOrderByScoreDescCreatedAtDesc(@Param("productNum") Long productNum);

    // 유저별 리뷰 최신순
    @Query("SELECT r FROM Review r WHERE r.user = :user ORDER BY r.createdAt DESC")
    List<Review> findByUserOrderByCreatedAtDesc(@Param("user") User user);

    // 유저 + 상품 기준 리뷰 최신순
    @Query("SELECT r FROM Review r WHERE r.user = :user AND r.productDetail.product.productNum = :productNum ORDER BY r.createdAt DESC")
    List<Review> findByUserAndProductNumOrderByCreatedAtDesc(@Param("user") User user, @Param("productNum") Long productNum);
}