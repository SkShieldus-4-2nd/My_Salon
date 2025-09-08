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
    public ResponseEntity<Long> createReview(@Valid @RequestBody ReviewDTO.Request request) {
        return ResponseEntity.ok(reviewService.createReview(request));
    }
    @GetMapping("/{id}")
    public ResponseEntity<ReviewDTO.Response> getReview(@PathVariable Long id) {
        return ResponseEntity.ok(reviewService.);
    }
}