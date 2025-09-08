package com.miniproject2.mysalon.repository;

import com.miniproject2.mysalon.entity.Comment;
import com.miniproject2.mysalon.entity.Post;
import com.miniproject2.mysalon.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByUser(User user);

    List<Comment> findByPost(Post post);
}
