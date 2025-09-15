package com.miniproject2.mysalon.controller;

import com.miniproject2.mysalon.controller.dto.CommentDTO;
import com.miniproject2.mysalon.security.CurrentUser;
import com.miniproject2.mysalon.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentDTO.Response> createComment(@CurrentUser Long userNum, @RequestBody @Valid CommentDTO.CommentRequest commentRequest) {
        CommentDTO.Response response = commentService.createComment(userNum,
                commentRequest
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{commentNum}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentNum) {
        commentService.deleteComment(commentNum);
        return ResponseEntity.noContent().build();
    }
}
