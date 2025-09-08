package com.miniproject2.mysalon.controller;

import com.miniproject2.mysalon.entity.ShoppingCart;
import com.miniproject2.mysalon.service.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    // 1. 장바구니에 상품 추가
    @PostMapping
    public ResponseEntity<ShoppingCart> addToCart(
            @RequestParam Long userNum,
            @RequestParam Long productDetailNum,
            @RequestParam int count) {
        ShoppingCart cart = shoppingCartService.addToCart(userNum, productDetailNum, count);
        return ResponseEntity.ok(cart);
    }

    // 2. 장바구니에서 상품 삭제
    @DeleteMapping
    public ResponseEntity<Void> removeFromCart(
            @RequestParam Long userNum,
            @RequestParam Long productDetailNum) {
        shoppingCartService.removeFromCart(userNum, productDetailNum);
        return ResponseEntity.noContent().build();
    }

    // 3. 유저별 장바구니 조회
    @GetMapping("/user/{userNum}")
    public ResponseEntity<List<ShoppingCart>> getUserCart(@PathVariable Long userNum) {
        // 조회 기능은 서비스에 구현 필요 (Optional)
        // List<ShoppingCart> carts = shoppingCartService.getUserCart(userNum);
        // return ResponseEntity.ok(carts);
        return ResponseEntity.status(501).build(); // 현재 미구현
    }
}
