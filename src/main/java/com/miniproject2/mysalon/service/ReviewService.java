package com.miniproject2.mysalon.service;

import com.miniproject2.mysalon.entity.*;
import com.miniproject2.mysalon.repository.ProductDetailRepository;
import com.miniproject2.mysalon.repository.ProductRepository;
import com.miniproject2.mysalon.repository.ReviewRepository;
import com.miniproject2.mysalon.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final ProductDetailRepository productDetailRepository;

    public Review createReview(Long userNum, Long productDetailNum, String text, Short score, String reviewImage) {
        User user = userRepository.findById(userNum).orElseThrow();
        ProductDetail productDetail = productDetailRepository.findById(productDetailNum).orElseThrow();

        Review review = Review.builder()
                .user(user)
                .productDetail(productDetail)
                .text(text)
                .score(score)
                .reviewImage(reviewImage)
                .build();
        return reviewRepository.save(review);
    }

    public void deleteReview(Long reviewNum) {
        reviewRepository.deleteById(reviewNum);
    }
}