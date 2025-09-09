package com.miniproject2.mysalon.service;

import com.miniproject2.mysalon.controller.dto.FavoriteDTO;
import com.miniproject2.mysalon.entity.Favorite;
import com.miniproject2.mysalon.entity.FavoriteId;
import com.miniproject2.mysalon.entity.Product;
import com.miniproject2.mysalon.entity.User;
import com.miniproject2.mysalon.repository.FavoriteRepository;
import com.miniproject2.mysalon.repository.ProductRepository;
import com.miniproject2.mysalon.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Transactional
    public FavoriteDTO.Response addFavorite(FavoriteDTO.Request request) {
        Long userNum = request.getUserNum();
        Long productNum = request.getProductNum();

        User user = userRepository.findById(userNum)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Product product = productRepository.findById(productNum)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        FavoriteId favoriteId = new FavoriteId(userNum, productNum);

        if (favoriteRepository.existsById(favoriteId)) {
            throw new RuntimeException("Already added to favorites");
        }

        Favorite favorite = Favorite.builder()
                .id(favoriteId)
                .user(user)
                .product(product)
                .build();

        Favorite savedFavorite = favoriteRepository.save(favorite);
        return FavoriteDTO.Response.fromEntity(savedFavorite);
    }

    @Transactional
    public void removeFavorite(FavoriteDTO.Request request) {
        FavoriteId favoriteId = new FavoriteId(request.getUserNum(), request.getProductNum());
        if (!favoriteRepository.existsById(favoriteId)) {
            throw new RuntimeException("Favorite not found");
        }
        favoriteRepository.deleteById(favoriteId);
    }

    // 유저별 찜 목록 조회
    @Transactional(readOnly = true)
    public List<Favorite> getUserFavorites(Long userNum) {
        User user = userRepository.findById(userNum)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return favoriteRepository.findByUser(user);
    }

}