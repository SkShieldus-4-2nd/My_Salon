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

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final ProductDetailRepository productDetailRepository;

    //리뷰 생성
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

    // 리뷰 수정
    @Transactional
    public ReviewDTO.Response editReview(Long reviewNum, ReviewDTO.Request request) {
        Review review = reviewRepository.findById(reviewNum)
                .orElseThrow(() -> new IllegalArgumentException("Review not found"));

        review.setText(request.getText());
        review.setScore(request.getScore());
        review.setReviewImage(request.getReviewImage());

        return ReviewDTO.Response.fromEntity(reviewRepository.save(review));
    }

    // 리뷰 삭제
    @Transactional
    public void deleteReview(Long reviewNum) {
        if (!reviewRepository.existsById(reviewNum)) {
            throw new IllegalArgumentException("Review not found");
        }
        reviewRepository.deleteById(reviewNum);
    }

    //유저 아이디로 리뷰 조회
    @Transactional(readOnly = true)
    public List<ReviewDTO.Response> getUserReviews(Long userNum) {
        User user = userRepository.findById(userNum)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return reviewRepository.findByUser(user)
                .stream()
                .map(ReviewDTO.Response::fromEntity)
                .toList();
    }

    // 특정 상품(Detail) 기준 리뷰 전체 조회
    @Transactional(readOnly = true)
    public List<ReviewDTO.Response> getAllReviewsByProductDetailNum(Long productDetailNum) {
        ProductDetail productDetail = productDetailRepository.findById(productDetailNum)
                .orElseThrow(() -> new IllegalArgumentException("Product detail not found"));

        return reviewRepository.findByProductDetail(productDetail)
                .stream()
                .map(ReviewDTO.Response::fromEntity)
                .collect(Collectors.toList());
    }

    //해당 상품에서 자신이 쓴 리뷰 로드
    @Transactional(readOnly = true)
    public List<ReviewDTO.Response> getUserReviewsByProductDetail(Long userNum, Long productDetailNum) {
        User user = userRepository.findById(userNum)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        ProductDetail productDetail = productDetailRepository.findById(productDetailNum)
                .orElseThrow(() -> new IllegalArgumentException("Product detail not found"));

        return reviewRepository.findByUserAndProductDetail(user, productDetail)
                .stream()
                .map(ReviewDTO.Response::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ReviewDTO.Response> getReviewsByUserSpecForProduct(Long productDetailNum,
                                                                   Short targetTall,
                                                                   Short targetWeight,
                                                                   Short tallRange,
                                                                   Short weightRange) {
        // 해당 상품의 리뷰만 조회
        List<Review> productReviews = reviewRepository.findByProductDetail_ProductDetailNum(productDetailNum);

        // 키/몸무게 조건 필터링
        return productReviews.stream()
                .filter(r -> {
                    Short userTall = r.getUser().getTall();
                    Short userWeight = r.getUser().getWeight();
                    boolean tallMatch = (userTall != null) && Math.abs(userTall - targetTall) <= tallRange;
                    boolean weightMatch = (userWeight != null) && Math.abs(userWeight - targetWeight) <= weightRange;
                    return tallMatch && weightMatch;
                })
                .map(ReviewDTO.Response::fromEntity)
                .collect(Collectors.toList());
    }

}


