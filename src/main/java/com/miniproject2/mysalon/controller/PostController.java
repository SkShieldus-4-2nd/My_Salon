package com.miniproject2.mysalon.controller;

import com.miniproject2.mysalon.controller.dto.PostDTO;
import com.miniproject2.mysalon.entity.Post;
import com.miniproject2.mysalon.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostDTO.Response> createPost(@Valid @RequestBody PostDTO.PostRequest request) {
        PostDTO.Response response = postService.createPost(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/coordi")
    public ResponseEntity<PostDTO.Response> createCoordiPost(@RequestBody PostDTO.PostRequest request) {
        PostDTO.Response response = postService.createCoordiPost(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<PostDTO.SimplePost>> getAllPost() {
        List<PostDTO.SimplePost> response = postService.getAllPosts();
        return ResponseEntity.ok(response);
    }
    @GetMapping("/coordi")
    public ResponseEntity<List<PostDTO.SimpleCoordiPost>> getAllCoordiPost() {
        List<PostDTO.SimpleCoordiPost> response = postService.getAllCoordiPosts();
        return ResponseEntity.ok(response);
    }


    @GetMapping("/hot-coordi")
    public ResponseEntity<List<PostDTO.SimpleCoordiPost>> getHotCoordiPost() {
        List<PostDTO.SimpleCoordiPost> response = postService.getHotCoordiPosts();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDTO.PostDetail> getPostDetail(@PathVariable Long postId) {
        PostDTO.PostDetail response = postService.getPostDetail(postId);
        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/{postNum}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postNum) {
        postService.deletePost(postNum);
        return ResponseEntity.noContent().build();
    }




}
