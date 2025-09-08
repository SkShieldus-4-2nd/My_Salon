package com.miniproject2.mysalon.controller;

import com.miniproject2.mysalon.controller.dto.ProductDTO;
import com.miniproject2.mysalon.controller.dto.ReviewDTO;
import com.miniproject2.mysalon.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ReviewDTO.Response> createReview(@RequestBody @Valid ReviewDTO.Request request) {
        ReviewDTO.Response response = reviewService.createReview(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{reviewNum}")
    public ResponseEntity<ReviewDTO.Response> getReview(@PathVariable Long reviewNum) {
        ReviewDTO.Response response = reviewService.getReview(reviewNum);
        return ResponseEntity.ok(response);
    }
}