package com.miniproject2.mysalon.controller;

import com.miniproject2.mysalon.entity.Post;
import com.miniproject2.mysalon.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // 1. 게시글 생성
    @PostMapping
    public ResponseEntity<Post> createPost(
            @RequestParam Long userNum,
            @RequestParam String title,
            @RequestParam String text) {
        Post post = postService.createPost(userNum, title, text);
        return ResponseEntity.ok(post);
    }

    // 2. 게시글 삭제
    @DeleteMapping("/{postNum}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postNum) {
        postService.deletePost(postNum);
        return ResponseEntity.noContent().build();
    }
}
