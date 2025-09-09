package com.miniproject2.mysalon.controller;

import com.miniproject2.mysalon.controller.dto.ProductDTO;
import com.miniproject2.mysalon.controller.dto.ReviewDTO;
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

    // 상품 디테일 기준 리뷰 전체 조회
    @GetMapping("/product/{productDetailNum}")
    public ResponseEntity<List<ReviewDTO.Response>> getReviewsByProductDetail(
            @PathVariable Long productDetailNum) {
        return ResponseEntity.ok(
                reviewService.getAllReviewsByProductDetailNum(productDetailNum)
        );
    }

    //해당 상품의 유저 본인의 리뷰 로드
    @GetMapping("/user/{userNum}/product/{productDetailNum}")
    public ResponseEntity<List<ReviewDTO.Response>> getUserReviewsForProduct(
            @PathVariable Long userNum,
            @PathVariable Long productDetailNum) {
        return ResponseEntity.ok(
                reviewService.getUserReviewsByProductDetail(userNum, productDetailNum)
        );
    }

    //키 몸무게로 리뷰 서치
    @GetMapping("/filter-by-spec")
    public ResponseEntity<List<ReviewDTO.Response>> getReviewsByUserSpec(
            @RequestParam Short tall,
            @RequestParam Short weight,
            @RequestParam(defaultValue = "3") Short tallRange,   // 기본 ±5cm
            @RequestParam(defaultValue = "3") Short weightRange  // 기본 ±5kg
    ) {
        List<ReviewDTO.Response> filteredReviews = reviewService.getReviewsByUserSpec(tall, weight, tallRange, weightRange);
        return ResponseEntity.ok(filteredReviews);
    }

}