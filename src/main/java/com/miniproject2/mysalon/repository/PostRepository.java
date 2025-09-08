package com.miniproject2.mysalon.repository;

import com.miniproject2.mysalon.entity.Post;
import com.miniproject2.mysalon.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByUser(User user);

    List<Post> findByTitleContaining(String keyword);
}
