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
    public ResponseEntity<FavoriteDTO.Response> addFavorite(@RequestBody FavoriteDTO.Request request) {
        FavoriteDTO.Response response = favoriteService.addFavorite(request);
        return ResponseEntity.ok(response);
    }

    // 찜 삭제
    @DeleteMapping
    public ResponseEntity<Void> removeFavorite(@RequestBody FavoriteDTO.Request request) {
        favoriteService.removeFavorite(request);
        return ResponseEntity.noContent().build();
    }

    // 유저별 찜 목록 조회
    @GetMapping("/{userNum}")
    public ResponseEntity<List<FavoriteDTO.Response>> getUserFavorites(@PathVariable Long userNum) {
        List<FavoriteDTO.Response> favorites = favoriteService.getUserFavorites(userNum)
                .stream()
                .map(FavoriteDTO.Response::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(favorites);
    }
}
