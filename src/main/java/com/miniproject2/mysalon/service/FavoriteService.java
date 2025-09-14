package com.miniproject2.mysalon.service;

import com.miniproject2.mysalon.controller.dto.FavoriteDTO;
import com.miniproject2.mysalon.controller.dto.PostDTO;
import com.miniproject2.mysalon.entity.*;
import com.miniproject2.mysalon.exception.BusinessException;
import com.miniproject2.mysalon.exception.ErrorCode;
import com.miniproject2.mysalon.repository.FavoriteRepository;
import com.miniproject2.mysalon.repository.ProductRepository;
import com.miniproject2.mysalon.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Transactional
    public FavoriteDTO.ClickResponse addFavorite(FavoriteDTO.FavoriteRequest favoriteRequest) {

        Long userNum = favoriteRequest.getUserNum();
        Long productNum = favoriteRequest.getProductNum();
        User user = userRepository.findById(userNum)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        Product product = productRepository.findById(productNum)
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));

        FavoriteId favoriteId = new FavoriteId(userNum, productNum);
        Optional<Favorite> like = favoriteRepository.findById(favoriteId);

        if (like.isPresent()) {
            product.setLikeCount(product.getLikeCount()-1);
            favoriteRepository.delete(like.get());
        } else {
            product.setLikeCount(product.getLikeCount()+1);
            Favorite savedLike = Favorite.builder()
                    .id(favoriteId)
                    .user(user)
                    .product(product)
                    .build();
            favoriteRepository.save(savedLike);
        }
        return FavoriteDTO.ClickResponse.fromEntity(product);

    }

    @Transactional
    public void removeFavorite(FavoriteDTO.FavoriteRequest favoriteRequest) {
        FavoriteId favoriteId = new FavoriteId(favoriteRequest.getUserNum(), favoriteRequest.getProductNum());
        if (!favoriteRepository.existsById(favoriteId)) {
            throw new RuntimeException("Favorite not found");
        }
        favoriteRepository.deleteById(favoriteId);
    }

    // 유저별 찜 목록 조회
    @Transactional(readOnly = true)
    public List<FavoriteDTO.Response> getUserFavorites(Long userNum) {
        User user = userRepository.findById(userNum)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Favorite> favorites = favoriteRepository.findByUser(user);

        // Favorite -> FavoriteDTO.Response 변환
        return favorites.stream()
                .map(FavoriteDTO.Response::fromEntity)
                .collect(Collectors.toList());
    }

    // 유저가 찜한 상품 개수
    @Transactional(readOnly = true)
    public Long getUserFavoriteCount(Long userNum) {
        User user = userRepository.findById(userNum)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return favoriteRepository.countByUser(user);
    }

    // 특정 상품을 찜한 유저 수
    @Transactional(readOnly = true)
    public Long getProductFavoriteCount(Long productNum) {
        return favoriteRepository.countByProductNum(productNum);
    }

}