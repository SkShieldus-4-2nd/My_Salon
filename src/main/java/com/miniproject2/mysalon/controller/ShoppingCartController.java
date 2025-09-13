package com.miniproject2.mysalon.controller;

import com.miniproject2.mysalon.controller.dto.ShoppingCartDTO;
import com.miniproject2.mysalon.entity.ShoppingCart;
import com.miniproject2.mysalon.security.CurrentUser;
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

    // 1. 장바구니에 상품 추가 //entity에 속성추가하지 말고 dto로만
    @PostMapping
    public ResponseEntity<ShoppingCartDTO.Response> addToCart(
            @RequestBody @Valid ShoppingCartDTO.CartRequest cartRequest, @CurrentUser Long userNum) {
        ShoppingCart cart = shoppingCartService.addToCart(userNum, cartRequest);
        return ResponseEntity.ok(ShoppingCartDTO.Response.fromEntity(cart));
    }

    // 2. 장바구니에서 상품 삭제
    @DeleteMapping
    public ResponseEntity<Void> removeFromCart(
            @RequestBody @Valid ShoppingCartDTO.CartRequest cartRequest, @CurrentUser Long userNum) {
        shoppingCartService.removeFromCart(cartRequest, userNum);
        return ResponseEntity.noContent().build();
    }

    // 3. 유저별 장바구니 조회 (Optional)
    @GetMapping("/{user-cart}")
    public ResponseEntity<List<ShoppingCartDTO.Response>> getUserCart(@CurrentUser Long userNum) {
        List<ShoppingCart> carts = shoppingCartService.getUserCart(userNum);
        List<ShoppingCartDTO.Response> responses = carts.stream()
                .map(ShoppingCartDTO.Response::fromEntity)
                .toList();
        return ResponseEntity.ok(responses);
    }

    // 4. 장바구니 물건 체크 변경 기능
    @PatchMapping("/selection")
    public ResponseEntity<ShoppingCart> updateCartSelection( @CurrentUser Long userNum,
            @RequestParam Long productDetailNum,
            @RequestParam boolean isSelected) {
        ShoppingCart updatedCart = shoppingCartService.updateSelection(userNum, productDetailNum, isSelected);
        return ResponseEntity.ok(updatedCart);
    }

    // 5. 장바구니에서 선택된 물건들의 총 가격 표시
    @GetMapping("/total")
    public ResponseEntity<Long> getTotalPrice(@CurrentUser Long userNum) {
        Long totalPrice = shoppingCartService.calculateTotalPrice(userNum);
        return ResponseEntity.ok(totalPrice);
    }

    @PatchMapping("/update-count")
    public ResponseEntity<ShoppingCart> updateItemCount(
            @CurrentUser Long userNum,
            @RequestParam Long productDetailNum,
            @RequestParam int count) {

        ShoppingCart updatedCart = shoppingCartService.updateItemCount(userNum, productDetailNum, count);
        return ResponseEntity.ok(updatedCart);
    }

}
