package com.miniproject2.mysalon.controller;

import com.miniproject2.mysalon.controller.dto.FavoriteDTO;
import com.miniproject2.mysalon.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/favorites")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    // 찜 추가
    @PostMapping
    public ResponseEntity<FavoriteDTO.ClickResponse> addFavorite(@RequestBody FavoriteDTO.FavoriteRequest favoriteRequest) {
        FavoriteDTO.ClickResponse response = favoriteService.addFavorite(favoriteRequest);
        return ResponseEntity.ok(response);
    }

    // 찜 삭제
    @DeleteMapping
    public ResponseEntity<Void> removeFavorite(@RequestBody FavoriteDTO.FavoriteRequest favoriteRequest) {
        favoriteService.removeFavorite(favoriteRequest);
        return ResponseEntity.noContent().build();
    }

    // 유저별 찜 목록 조회
    @GetMapping("/{userNum}")
    public ResponseEntity<List<FavoriteDTO.Response>> getUserFavorites(@PathVariable Long userNum) {
        List<FavoriteDTO.Response> favorites = favoriteService.getUserFavorites(userNum);
        return ResponseEntity.ok(favorites);
    }

    // 유저가 찜한 상품 개수 조회
    @GetMapping("/user/{userNum}/count")
    public ResponseEntity<Long> getUserFavoriteCount(@PathVariable Long userNum) {
        return ResponseEntity.ok(favoriteService.getUserFavoriteCount(userNum));
    }

    // 특정 상품 찜 개수 조회
    @GetMapping("/product/{productNum}/count")
    public ResponseEntity<Long> getProductFavoriteCount(@PathVariable Long productNum) {
        return ResponseEntity.ok(favoriteService.getProductFavoriteCount(productNum));
    }
}
