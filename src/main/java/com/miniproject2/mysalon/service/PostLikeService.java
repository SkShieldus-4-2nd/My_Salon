package com.miniproject2.mysalon.service;

import com.miniproject2.mysalon.controller.dto.PostLikeDTO;
import com.miniproject2.mysalon.entity.Post;
import com.miniproject2.mysalon.entity.PostLike;
import com.miniproject2.mysalon.entity.PostLikeId;
import com.miniproject2.mysalon.entity.User;
import com.miniproject2.mysalon.repository.PostLikeRepository;
import com.miniproject2.mysalon.repository.PostRepository;
import com.miniproject2.mysalon.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostLikeService {

    private final PostLikeRepository postLikeRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional
    public void likePost(PostLikeDTO.Request request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        Post post = postRepository.findById(request.getPostId())
                .orElseThrow(() -> new EntityNotFoundException("Post not found"));

        PostLikeId postLikeId = new PostLikeId(user.getUserNum(), post.getPostNum());
        if (postLikeRepository.existsById(postLikeId)) {
            throw new IllegalArgumentException("Already liked");
        }

        PostLike postLike = PostLike.builder()
                .id(postLikeId)
                .user(user)
                .post(post)
                .build();

        postLikeRepository.save(postLike);

        post.setLikeCount(post.getLikeCount() + 1);
        postRepository.save(post);
    }

    @Transactional
    public void unlikePost(PostLikeDTO.Request request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        Post post = postRepository.findById(request.getPostId())
                .orElseThrow(() -> new EntityNotFoundException("Post not found"));

        PostLikeId postLikeId = new PostLikeId(user.getUserNum(), post.getPostNum());
        PostLike postLike = postLikeRepository.findById(postLikeId)
                .orElseThrow(() -> new EntityNotFoundException("Like not found"));

        postLikeRepository.delete(postLike);

        post.setLikeCount(post.getLikeCount() - 1);
        postRepository.save(post);
    }

    public boolean isPostLikedByUser(Long postId, Long userId) {
        return postLikeRepository.existsById(new PostLikeId(userId, postId));
    }
}
