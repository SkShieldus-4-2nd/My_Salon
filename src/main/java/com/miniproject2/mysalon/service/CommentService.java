package com.miniproject2.mysalon.service;

import com.miniproject2.mysalon.controller.dto.CommentDTO;
import com.miniproject2.mysalon.entity.Comment;
import com.miniproject2.mysalon.entity.Post;
import com.miniproject2.mysalon.entity.User;
import com.miniproject2.mysalon.repository.CommentRepository;
import com.miniproject2.mysalon.repository.PostRepository;
import com.miniproject2.mysalon.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public CommentDTO.Response createComment(Long userNum, Long postNum, String text) {
        User user = userRepository.findById(userNum).orElseThrow();
        Post post = postRepository.findById(postNum).orElseThrow();

        Comment comment = Comment.builder()
                .user(user)
                .post(post)
                .text(text)
                .build();

        Comment saved = commentRepository.save(comment);
        return CommentDTO.Response.fromEntity(saved);
    }

    public void deleteComment(Long commentNum) {
        commentRepository.deleteById(commentNum);
    }
}
