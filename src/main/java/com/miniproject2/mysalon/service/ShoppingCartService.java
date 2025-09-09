package com.miniproject2.mysalon.service;

import com.miniproject2.mysalon.controller.dto.ShoppingCartDTO;
import com.miniproject2.mysalon.entity.*;
import com.miniproject2.mysalon.repository.ProductDetailRepository;
import com.miniproject2.mysalon.repository.ShoppingCartRepository;
import com.miniproject2.mysalon.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final UserRepository userRepository;
    private final ProductDetailRepository productDetailRepository;

    // 장바구니 추가
    public ShoppingCart addToCart(ShoppingCartDTO.Request request) {
        User user = userRepository.findById(request.getUserNum()).orElseThrow();
        ProductDetail productDetail = productDetailRepository.findById(request.getProductDetailNum()).orElseThrow();

        ShoppingCartKey key = new ShoppingCartKey(request.getUserNum(), request.getProductDetailNum());
        ShoppingCart cart = new ShoppingCart();
        cart.setId(key);
        cart.setUser(user);
        cart.setProductDetail(productDetail);
        cart.setCount(request.getCount());

        return shoppingCartRepository.save(cart);
    }

    // 장바구니 삭제
    public void removeFromCart(ShoppingCartDTO.Request request) {
        ShoppingCartKey key = new ShoppingCartKey(request.getUserNum(), request.getProductDetailNum());
        shoppingCartRepository.deleteById(key);
    }

    @Transactional(readOnly = true)
    public List<ShoppingCart> getUserCart(Long userNum) {
        User user = userRepository.findById(userNum).orElseThrow();
        return shoppingCartRepository.findByUser(user);
    }
}