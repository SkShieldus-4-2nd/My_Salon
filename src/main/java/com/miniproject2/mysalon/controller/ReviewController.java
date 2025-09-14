package com.miniproject2.mysalon.controller;

import com.miniproject2.mysalon.controller.dto.ProductDTO;
import com.miniproject2.mysalon.controller.dto.ReviewDTO;
import com.miniproject2.mysalon.security.CurrentUser;
import com.miniproject2.mysalon.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    //리뷰 생성
    @PostMapping
    public ResponseEntity<ReviewDTO.Response> createReview(@RequestBody @Valid ReviewDTO.Request request) {
        ReviewDTO.Response response = reviewService.createReview(request);
        return ResponseEntity.ok(response);
    }

    // 리뷰 수정
    @PutMapping("/{reviewNum}")
    public ResponseEntity<ReviewDTO.Response> editReview(
            @PathVariable Long reviewNum,
            @RequestBody ReviewDTO.Request request) {
        return ResponseEntity.ok(reviewService.editReview(reviewNum, request));
    }

    // 리뷰 삭제
    @DeleteMapping("/{reviewNum}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long reviewNum) {
        reviewService.deleteReview(reviewNum);
        return ResponseEntity.noContent().build();
    }

    //유저의 모든 리뷰 조회
    @GetMapping("/user/{userNum}")
    public ResponseEntity<List<ReviewDTO.Response>> getUserReviews(@PathVariable Long userNum) {
        List<ReviewDTO.Response> reviews = reviewService.getUserReviews(userNum);
        return ResponseEntity.ok(reviews);
    }

    // 상품 기준 리뷰 전체 조회
    @GetMapping("/product/{productNum}")
    public ResponseEntity<List<ReviewDTO.Response>> getReviewsByProduct(
            @PathVariable Long productNum) {
        return ResponseEntity.ok(
                reviewService.getAllReviewsByProductNum(productNum)
        );
    }

    // 해당 상품에서 유저 본인의 리뷰 로드
    @GetMapping("/user/{userNum}/product/{productNum}")
    public ResponseEntity<List<ReviewDTO.Response>> getUserReviewsForProduct(
            @PathVariable Long userNum,
            @PathVariable Long productNum) {
        return ResponseEntity.ok(
                reviewService.getUserReviewsByProduct(userNum, productNum)
        );
    }

    // 유저 스펙 기반 특정 상품 리뷰 조회
    @GetMapping("/filter-by-spec/product/{productNum}")
    public ResponseEntity<List<ReviewDTO.Response>> getReviewsByUserSpecForProduct(
            @PathVariable Long productNum,
            @RequestParam Short tall,
            @RequestParam Short weight,
            @RequestParam(defaultValue = "3") Short tallRange,
            @RequestParam(defaultValue = "3") Short weightRange
    ) {
        List<ReviewDTO.Response> filteredReviews = reviewService
                .getReviewsByUserSpecForProduct(productNum, tall, weight, tallRange, weightRange);
        return ResponseEntity.ok(filteredReviews);
    }

    //별점 기준 조회 같은 별점 내에서는 최신순
    @GetMapping("/product/{productNum}/by-score")
    public ResponseEntity<List<ReviewDTO.Response>> getReviewsByProductSortedByScore(
            @PathVariable Long productNum) {
        return ResponseEntity.ok(
                reviewService.getReviewsByProductSortedByScore(productNum)
        );
    }

    //제품 평점 조회
    @GetMapping("/product/{productNum}/average-score")
    public ResponseEntity<Double> getAverageScore(@PathVariable Long productNum) {
        Double averageScore = reviewService.getAverageScoreByProductNum(productNum);
        return ResponseEntity.ok(averageScore);
    }

    // 유저 본인이 작성한 리뷰 개수 조회
    @GetMapping("/user/{userNum}/count")
    public ResponseEntity<Long> getUserReviewCount(@PathVariable Long userNum) {
        Long count = reviewService.getUserReviewCount(userNum);
        return ResponseEntity.ok(count);
    }

    // 특정 상품(Product) 리뷰 개수 조회
    @GetMapping("/product/{productNum}/count")
    public ResponseEntity<Long> getProductReviewCount(@PathVariable Long productNum) {
        Long count = reviewService.getProductReviewCount(productNum);
        return ResponseEntity.ok(count);
    }


}