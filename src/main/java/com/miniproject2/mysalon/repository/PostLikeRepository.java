package com.miniproject2.mysalon.repository;

import com.miniproject2.mysalon.entity.PostLike;
import com.miniproject2.mysalon.entity.PostLikeId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, PostLikeId> {
}
