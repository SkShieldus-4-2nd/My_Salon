package com.miniproject2.mysalon.service;

import com.miniproject2.mysalon.controller.dto.PostDTO;
import com.miniproject2.mysalon.controller.dto.PostLikeDTO;
import com.miniproject2.mysalon.entity.*;
import com.miniproject2.mysalon.exception.BusinessException;
import com.miniproject2.mysalon.exception.ErrorCode;
import com.miniproject2.mysalon.repository.PostLikeRepository;
import com.miniproject2.mysalon.repository.PostRepository;
import com.miniproject2.mysalon.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostLikeService {

    private final PostLikeRepository postLikeRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;


    public PostDTO.SimpleCoordiPost clickPost(PostLikeDTO.ClickRequest request, Long userId) {

        Post post = postRepository.findById(request.getPostNum())
                .orElseThrow(() -> new BusinessException(ErrorCode.POST_NOT_FOUND));
        PostLikeId postLikeId = new PostLikeId(userId, request.getPostNum());
        Optional<PostLike> postLike = postLikeRepository.findById(postLikeId);

        if (post.getPostType() != PostType.COORDI) {
            throw new BusinessException(ErrorCode.POST_TYPE_WRONG);
        }

        if (postLike.isEmpty()) {
            post.setLikeCount(post.getLikeCount()+1);
            User user = userRepository.findById(userId).get();
            PostLike savedPostLike = PostLike.builder()
                    .id(postLikeId)
                    .user(user)
                    .post(post)
                    .build();
            postLikeRepository.save(savedPostLike);
        } else {
            post.setLikeCount(post.getLikeCount()-1);
            postLikeRepository.delete(postLike.get());
        }
        return fromEntity(post);

    }

    public boolean isPostLikedByUser(Long postId, Long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new BusinessException(ErrorCode.POST_NOT_FOUND));
        if (post.getPostType() != PostType.COORDI) {
            throw new BusinessException(ErrorCode.POST_TYPE_WRONG);
        }
        return postLikeRepository.existsById(new PostLikeId(userId, postId));
    }

    public PostDTO.SimpleCoordiPost fromEntity(Post post) {
        return PostDTO.SimpleCoordiPost.builder()
                .coordiImage(post.getImage())
                .title(post.getTitle())
                .writer(post.getUser().getUserName())
                .likeCount(post.getLikeCount())
                .isLiked(isPostLikedByUser(post.getPostNum(), post.getUser().getUserNum()))
                .build();
    }
}
