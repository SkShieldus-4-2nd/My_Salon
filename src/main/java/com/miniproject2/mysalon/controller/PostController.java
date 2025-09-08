package com.miniproject2.mysalon.controller;

import com.miniproject2.mysalon.controller.dto.PostDTO;
import com.miniproject2.mysalon.entity.Post;
import com.miniproject2.mysalon.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostDTO.Response> createPost(@RequestBody @Valid PostDTO.Request request) {
        PostDTO.Response response = postService.createPost(
                request.getUserNum(),
                request.getTitle(),
                request.getText()
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{postNum}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postNum) {
        postService.deletePost(postNum);
        return ResponseEntity.noContent().build();
    }
}
