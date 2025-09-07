package com.miniproject2.mysalon.controller;

import com.miniproject2.mysalon.repository.PostRepository;
import com.miniproject2.mysalon.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
}
