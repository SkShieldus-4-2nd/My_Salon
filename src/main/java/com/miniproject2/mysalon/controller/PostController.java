package com.miniproject2.mysalon.controller;

import com.miniproject2.mysalon.controller.dto.PostDTO;
import com.miniproject2.mysalon.entity.Post;
import com.miniproject2.mysalon.security.CurrentUser;
import com.miniproject2.mysalon.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    @PreAuthorize("hasAuthority('SELLER') or hasAuthority('BUYER')")
    public ResponseEntity<PostDTO.Response> createPost(@CurrentUser Long user, @Valid @RequestBody PostDTO.PostRequest request) {
        PostDTO.Response response = postService.createPost(user,request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/coordi")
    @PreAuthorize("hasAuthority('SELLER') or hasAuthority('BUYER')")
    public ResponseEntity<PostDTO.Response> createCoordiPost(@CurrentUser Long user, @RequestBody PostDTO.PostRequest request) {
        PostDTO.Response response = postService.createCoordiPost(user,request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('SELLER') or hasAuthority('BUYER')")
    public ResponseEntity<List<PostDTO.SimplePost>> getAllPost() {
        List<PostDTO.SimplePost> response = postService.getAllPosts();
        return ResponseEntity.ok(response);
    }
    @GetMapping("/coordi")
    @PreAuthorize("hasAuthority('SELLER') or hasAuthority('BUYER')")
    public ResponseEntity<List<PostDTO.SimpleCoordiPost>> getAllCoordiPost(@CurrentUser Long userNum) {
        List<PostDTO.SimpleCoordiPost> response = postService.getAllCoordiPosts(userNum);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/hot-coordi")
    @PreAuthorize("hasAuthority('SELLER') or hasAuthority('BUYER')")
    public ResponseEntity<List<PostDTO.SimpleCoordiPost>> getHotCoordiPost() {
        List<PostDTO.SimpleCoordiPost> response = postService.getHotCoordiPosts();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{postId}")
    @PreAuthorize("hasAuthority('SELLER') or hasAuthority('BUYER')")
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
