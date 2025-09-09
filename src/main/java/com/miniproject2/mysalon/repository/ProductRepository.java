package com.miniproject2.mysalon.repository;

import com.miniproject2.mysalon.entity.Category;
import com.miniproject2.mysalon.entity.CategoryLow;
import com.miniproject2.mysalon.entity.Gender;
import com.miniproject2.mysalon.entity.Product;
import com.miniproject2.mysalon.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    List<Product> findByCategory(Category category);

    List<Product> findByGender(Gender gender);

    List<Product> findByCategoryAndCategoryLow(Category category, CategoryLow categoryLow);

    List<Product> findByUser(User user);
}
