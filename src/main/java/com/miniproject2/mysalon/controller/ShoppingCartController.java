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

    // 장바구니에 상품 추가
    @PostMapping
    public ResponseEntity<ShoppingCartDTO.Response> addToCart(
            @RequestBody @Valid ShoppingCartDTO.Request request) {
        ShoppingCartDTO.Response response = shoppingCartService.addToCart(request);
        return ResponseEntity.ok(response);
    }

    // 장바구니에서 상품 삭제
    @DeleteMapping
    public ResponseEntity<Void> removeFromCart(
            @RequestBody @Valid ShoppingCartDTO.Request request) {
        shoppingCartService.removeFromCart(request);
        return ResponseEntity.noContent().build();
    }

    // 장바구니 불러오기
    @GetMapping("/{userNum}")
    public ResponseEntity<List<ShoppingCartDTO.Response>> getCart(@PathVariable Long userNum) {
        List<ShoppingCartDTO.Response> cartDTOs = shoppingCartService.getUserCartDTO(userNum);
        return ResponseEntity.ok(cartDTOs);
    }

    // 장바구니 선택 변경
    @PatchMapping("/selection")
    public ResponseEntity<ShoppingCartDTO.Response> updateCartSelection(
            @RequestParam Long userNum,
            @RequestParam Long productDetailNum,
            @RequestParam boolean isSelected) {
        ShoppingCartDTO.Response response =
                shoppingCartService.updateSelection(userNum, productDetailNum, isSelected);
        return ResponseEntity.ok(response);
    }

    // 총합 가격
    @GetMapping("/total")
    public ResponseEntity<Long> getTotalPrice(@RequestParam Long userNum) {
        Long totalPrice = shoppingCartService.calculateTotalPrice(userNum);
        return ResponseEntity.ok(totalPrice);
    }

    // 수량 변경
    @PatchMapping("/update-count")
    public ResponseEntity<ShoppingCartDTO.Response> updateItemCount(
            @RequestParam Long userNum,
            @RequestParam Long productDetailNum,
            @RequestParam int count) {
        ShoppingCartDTO.Response response =
                shoppingCartService.updateItemCount(userNum, productDetailNum, count);
        return ResponseEntity.ok(response);
    }

}
