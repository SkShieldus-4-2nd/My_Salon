package com.miniproject2.mysalon.controller;

import com.miniproject2.mysalon.controller.dto.PostLikeDTO;
import com.miniproject2.mysalon.service.PostLikeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/post-likes")
@RequiredArgsConstructor
public class PostLikeController {

    private final PostLikeService postLikeService;

    @PostMapping
    public ResponseEntity<Void> likePost(@Valid @RequestBody PostLikeDTO.Request request) {
        postLikeService.likePost(request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> unlikePost(@Valid @RequestBody PostLikeDTO.Request request) {
        postLikeService.unlikePost(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Boolean> isPostLikedByUser(@RequestParam Long postId, @RequestParam Long userId) {
        return ResponseEntity.ok(postLikeService.isPostLikedByUser(postId, userId));
    }
}
