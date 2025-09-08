package com.miniproject2.mysalon.repository;

import com.miniproject2.mysalon.entity.Category;
import com.miniproject2.mysalon.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategory(Category category);

    List<Product> findByGender(String gender);

    List<Product> findByProductNameContaining(String keyword);
}
