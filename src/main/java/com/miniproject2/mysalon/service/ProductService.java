package com.miniproject2.mysalon.service;

import com.miniproject2.mysalon.controller.dto.ProductDTO;
import com.miniproject2.mysalon.entity.Category;
import com.miniproject2.mysalon.entity.CategoryLow;
import com.miniproject2.mysalon.entity.Product;

import java.util.List;

public interface ProductService {
    Product createProduct(ProductDTO productDTO);
    Product editProduct(Long productId, ProductDTO productDTO);
    void deleteProduct(Long productId);
    List<Product> searchProductByProductName(String keyword);
    List<Product> searchProductsByCategoryAndCategoryLow(Category category, CategoryLow categoryLow);
    List<Product> searchProductsByUserNum(Long userNum);
}
