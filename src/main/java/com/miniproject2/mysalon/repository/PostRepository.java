package com.miniproject2.mysalon.repository;

import com.miniproject2.mysalon.controller.dto.PostDTO;
import com.miniproject2.mysalon.entity.Post;
import com.miniproject2.mysalon.entity.PostType;
import com.miniproject2.mysalon.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByUser(User user);

    List<Post> findByTitleContaining(String keyword);

    @Query("SELECT p FROM Post p WHERE p.postType = :postType ORDER BY p.likeCount DESC")
    List<Post> findTop10ByLikeCountDesc(@Param("postType") PostType postType, Pageable pageable);

    @Query("SELECT p FROM Post p WHERE p.postType = :postType")
    List<Post> findPostsByPostTypeJPQL(@Param("postType") PostType postType);
}