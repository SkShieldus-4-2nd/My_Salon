package com.miniproject2.mysalon.service;

import com.miniproject2.mysalon.controller.dto.ShoppingCartDTO;
import com.miniproject2.mysalon.entity.*;
import com.miniproject2.mysalon.exception.BusinessException;
import com.miniproject2.mysalon.exception.ErrorCode;
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

    // 장바구니 물건 추가
    public ShoppingCartDTO.Response addToCart(Long userNum, ShoppingCartDTO.CartRequest request) {
        User user = userRepository.findById(userNum).orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        ProductDetail productDetail = productDetailRepository.findById(request.getProductDetailNum()).orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));

        ShoppingCartKey key = new ShoppingCartKey(userNum, request.getProductDetailNum());
        ShoppingCart cart = new ShoppingCart();
        cart.setId(key);
        cart.setUser(user);
        cart.setProductDetail(productDetail);
        cart.setCount(request.getCount());
        cart.setSelected(true);

        ShoppingCart savedCart = shoppingCartRepository.save(cart);

        // ✅ DTO 변환 시 productName, productPrice, size, color 자동 채워짐
        return ShoppingCartDTO.Response.fromEntity(savedCart);
    }

    // 장바구니 물건 삭제
    public void removeFromCart(ShoppingCartDTO.CartRequest cartRequest, Long userNum) {
        ShoppingCartKey key = new ShoppingCartKey(userNum, cartRequest.getProductDetailNum());
        shoppingCartRepository.deleteById(key);
    }

    //유저 장바구니 로드
    @Transactional(readOnly = true)
    public List<ShoppingCartDTO.Response> getUserCartDTO(Long userNum) {
        User user = userRepository.findById(userNum)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        List<ShoppingCart> cartItems = shoppingCartRepository.findByUser(user);

        return cartItems.stream()
                .map(ShoppingCartDTO.Response::fromEntity) // ✅ 여기서 productName 등 채워짐
                .toList();
    }

    // 장바구니 선택 변경
    @Transactional
    public ShoppingCartDTO.Response updateSelection(Long userNum, Long productDetailNum, boolean isSelected) {
        ShoppingCartKey key = new ShoppingCartKey(userNum, productDetailNum);
        ShoppingCart cart = shoppingCartRepository.findById(key)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        cart.setSelected(isSelected);
        ShoppingCart savedCart = shoppingCartRepository.save(cart);
        return ShoppingCartDTO.Response.fromEntity(savedCart);
    }

    // ✅ 총 가격 계산
    @Transactional(readOnly = true)
    public Long calculateTotalPrice(Long userNum) {
        User user = userRepository.findById(userNum)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<ShoppingCart> cartItems = shoppingCartRepository.findByUser(user);

        return cartItems.stream()
                .filter(ShoppingCart::isSelected)
                .mapToLong(item -> item.getProductDetail().getProduct().getPrice() * item.getCount()) // ✅ Product에서 가격 가져오기
                .sum();
    }


    // 수량 변경
    public ShoppingCartDTO.Response updateItemCount(Long userNum, Long productDetailNum, int count) {
        ShoppingCartKey key = new ShoppingCartKey(userNum, productDetailNum);
        ShoppingCart cart = shoppingCartRepository.findById(key)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        cart.setCount(count);
        ShoppingCart savedCart = shoppingCartRepository.save(cart);
        return ShoppingCartDTO.Response.fromEntity(savedCart);
    }
}