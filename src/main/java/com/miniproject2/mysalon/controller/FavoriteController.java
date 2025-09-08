package com.miniproject2.mysalon.controller;

import com.miniproject2.mysalon.entity.Favorite;
import com.miniproject2.mysalon.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorites")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    // 1. 상품 찜 추가
    @PostMapping
    public ResponseEntity<Favorite> addFavorite(
            @RequestParam Long userNum,
            @RequestParam Long productId) {
        Favorite favorite = favoriteService.addFavorite(userNum, productId);
        return ResponseEntity.ok(favorite);
    }

    // 2. 상품 찜 해제
    @DeleteMapping
    public ResponseEntity<Void> removeFavorite(
            @RequestParam Long userNum,
            @RequestParam Long productId) {
        favoriteService.removeFavorite(userNum, productId);
        return ResponseEntity.noContent().build();
    }

    // 3. 유저별 찜 목록 조회
    @GetMapping("/user/{userNum}")
    public ResponseEntity<List<Favorite>> getUserFavorites(@PathVariable Long userNum) {
        List<Favorite> favorites = favoriteService.getUserFavorites(userNum);
        return ResponseEntity.ok(favorites);
    }
}
