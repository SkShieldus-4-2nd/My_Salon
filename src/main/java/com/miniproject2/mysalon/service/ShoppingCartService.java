package com.miniproject2.mysalon.service;

import com.miniproject2.mysalon.entity.*;
import com.miniproject2.mysalon.repository.ProductDetailRepository;
import com.miniproject2.mysalon.repository.ShoppingCartRepository;
import com.miniproject2.mysalon.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final UserRepository userRepository;
    private final ProductDetailRepository productDetailRepository;

    // 장바구니 추가
    public ShoppingCart addToCart(Long userNum, Long productDetailNum, int count) {
        User user = userRepository.findById(userNum).orElseThrow();
        ProductDetail productDetail = productDetailRepository.findById(productDetailNum).orElseThrow();

        ShoppingCartKey key = new ShoppingCartKey(userNum, productDetailNum);
        ShoppingCart cart = new ShoppingCart();
        cart.setId(key);
        cart.setUser(user);
        cart.setProductDetail(productDetail);
        cart.setCount(count);

        return shoppingCartRepository.save(cart);
    }

    // 장바구니 삭제
    public void removeFromCart(Long userNum, Long productDetailNum) {
        ShoppingCartKey key = new ShoppingCartKey(userNum, productDetailNum);
        shoppingCartRepository.deleteById(key);
    }
}