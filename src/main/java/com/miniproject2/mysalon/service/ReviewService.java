package com.miniproject2.mysalon.service;

import com.miniproject2.mysalon.controller.dto.ReviewDTO;
import com.miniproject2.mysalon.entity.ProductDetail;
import com.miniproject2.mysalon.entity.Review;
import com.miniproject2.mysalon.entity.User;
import com.miniproject2.mysalon.repository.ProductDetailRepository;
import com.miniproject2.mysalon.repository.ReviewRepository;
import com.miniproject2.mysalon.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final ProductDetailRepository productDetailRepository;

    @Transactional
    public ReviewDTO.Response createReview(ReviewDTO.Request request) {
        User user = userRepository.findById(request.getUserNum())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        ProductDetail productDetail = productDetailRepository.findById(request.getProductDetailNum())
                .orElseThrow(() -> new IllegalArgumentException("Product detail not found"));

        Review review = Review.builder()
                .user(user)
                .productDetail(productDetail)
                .text(request.getText())
                .score(request.getScore())
                .reviewImage(request.getReviewImage())
                .build();

        Review savedReview = reviewRepository.save(review);
        return ReviewDTO.Response.fromEntity(savedReview);
    }

    @Transactional(readOnly = true)
    public ReviewDTO.Response getReview(Long reviewNum) {
        Review review = reviewRepository.findById(reviewNum)
                .orElseThrow(() -> new IllegalArgumentException("Review not found"));
        return ReviewDTO.Response.fromEntity(review);
    }


}