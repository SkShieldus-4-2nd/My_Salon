package com.miniproject2.mysalon.controller;

import com.miniproject2.mysalon.controller.dto.PostDTO;
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

    @PutMapping("/{id}")
    public ResponseEntity<PostDTO.SimpleCoordiPost> clickLike(@PathVariable Long id, @RequestBody PostLikeDTO.ClickRequest request) {
        PostDTO.SimpleCoordiPost response = postLikeService.clickPost(request, id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Boolean> isPostLikedByUser(@RequestParam Long postId, @RequestParam Long userId) {
        return ResponseEntity.ok(postLikeService.isPostLikedByUser(postId, userId));
    }
}
