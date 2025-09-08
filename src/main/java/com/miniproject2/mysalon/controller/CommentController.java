package com.miniproject2.mysalon.controller;

import com.miniproject2.mysalon.entity.Comment;
import com.miniproject2.mysalon.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // 댓글 생성
    @PostMapping
    public ResponseEntity<Comment> createComment(
            @RequestParam Long userNum,
            @RequestParam Long postNum,
            @RequestParam String text) {
        Comment comment = commentService.createComment(userNum, postNum, text);
        return ResponseEntity.ok(comment);
    }

    // 댓글 삭제
    @DeleteMapping("/{commentNum}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentNum) {
        commentService.deleteComment(commentNum);
        return ResponseEntity.noContent().build();
    }
}
