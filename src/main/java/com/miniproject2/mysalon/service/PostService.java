package com.miniproject2.mysalon.service;

import com.miniproject2.mysalon.controller.dto.PostDTO;
import com.miniproject2.mysalon.entity.Post;
import com.miniproject2.mysalon.entity.PostType;
import com.miniproject2.mysalon.entity.User;
import com.miniproject2.mysalon.exception.BusinessException;
import com.miniproject2.mysalon.exception.ErrorCode;
import com.miniproject2.mysalon.repository.PostRepository;
import com.miniproject2.mysalon.repository.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
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

    public void deletePost(Long postNum) {
        postRepository.deleteById(postNum);
    }


}