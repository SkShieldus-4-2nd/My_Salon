package com.miniproject2.mysalon.service;

import com.miniproject2.mysalon.controller.dto.PostDTO;
import com.miniproject2.mysalon.entity.*;
import com.miniproject2.mysalon.exception.BusinessException;
import com.miniproject2.mysalon.exception.ErrorCode;
import com.miniproject2.mysalon.repository.PostLikeRepository;
import com.miniproject2.mysalon.repository.PostRepository;
import com.miniproject2.mysalon.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;


    public PostDTO.Response createPost(PostDTO.PostRequest request) {

        User user = userRepository.findById(request.getUserNum())
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        Post post = Post.builder()
                .user(user)
                .title(request.getTitle())
                .text(request.getText())
                .likeCount(null)
                .postType(PostType.POST)
                .image(request.getImage())
                .build();
        Post saved = postRepository.save(post);

        return PostDTO.Response.fromEntity(saved);
    }

    public PostDTO.Response createCoordiPost(PostDTO.PostRequest request) {

        User user = userRepository.findById(request.getUserNum())
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        Post post = Post.builder()
                .user(user)
                .title(request.getTitle())
                .text(request.getText())
                .likeCount(0L)
                .postType(PostType.COORDI)
                .image(request.getImage())
                .build();
        Post saved = postRepository.save(post);

        return PostDTO.Response.fromEntity(saved);

    }

    public List<PostDTO.SimplePost> getAllPosts() {
        return postRepository.findPostsByPostTypeJPQL(PostType.POST)
                .stream()
                .map(PostDTO.SimplePost::fromEntity)
                .toList();

    }

    public List<PostDTO.SimpleCoordiPost> getAllCoordiPosts() {
        return  postRepository.findPostsByPostTypeJPQL(PostType.COORDI)
                .stream()
                .map(PostDTO.SimpleCoordiPost::fromEntity)
                .toList();

    }

    public List<PostDTO.SimpleCoordiPost> getHotCoordiPosts() {

        Pageable top10 = PageRequest.of(0,10, Sort.by("likeCount").descending());
        return postRepository.findTop10ByLikeCountDesc(top10)
                .stream()
                .map(PostDTO.SimpleCoordiPost::fromEntity)
                .toList();
    }

    public PostDTO.PostDetail getPostDetail(Long postId) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new BusinessException(ErrorCode.POST_NOT_FOUND));
        return PostDTO.PostDetail.fromEntity(post);
    }

    public void deletePost(Long postNum) {
        postRepository.deleteById(postNum);
    }



}