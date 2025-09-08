package com.miniproject2.mysalon.repository;

import com.miniproject2.mysalon.entity.Product;
import com.miniproject2.mysalon.entity.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDetailRepository extends JpaRepository<ProductDetail, Long> {

    List<ProductDetail> findByProduct(Product product);
}
