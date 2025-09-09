package com.miniproject2.mysalon.controller;

import com.miniproject2.mysalon.controller.dto.ShoppingCartDTO;
import com.miniproject2.mysalon.entity.ShoppingCart;
import com.miniproject2.mysalon.service.ShoppingCartService;
import jakarta.validation.Valid;
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
    public ResponseEntity<ShoppingCartDTO.Response> addToCart(
            @RequestBody @Valid ShoppingCartDTO.Request request) {
        ShoppingCart cart = shoppingCartService.addToCart(request);
        return ResponseEntity.ok(ShoppingCartDTO.Response.fromEntity(cart));
    }

    // 2. 장바구니에서 상품 삭제
    @DeleteMapping
    public ResponseEntity<Void> removeFromCart(
            @RequestBody @Valid ShoppingCartDTO.Request request) {
        shoppingCartService.removeFromCart(request);
        return ResponseEntity.noContent().build();
    }

    // 3. 유저별 장바구니 조회 (Optional)
    @GetMapping("/{userNum}")
    public ResponseEntity<List<ShoppingCartDTO.Response>> getUserCart(@PathVariable Long userNum) {
        List<ShoppingCart> carts = shoppingCartService.getUserCart(userNum);
        List<ShoppingCartDTO.Response> responses = carts.stream()
                .map(ShoppingCartDTO.Response::fromEntity)
                .toList();
        return ResponseEntity.ok(responses);
    }
}
