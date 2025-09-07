package com.miniproject2.mysalon.controller;

import com.miniproject2.mysalon.repository.ReviewRepository;
import com.miniproject2.mysalon.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
}
