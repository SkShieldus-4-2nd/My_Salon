package com.miniproject2.mysalon.repository;

import com.miniproject2.mysalon.entity.Post;
import com.miniproject2.mysalon.entity.PostLike;
import com.miniproject2.mysalon.entity.PostLikeId;
import com.miniproject2.mysalon.entity.PostType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, PostLikeId> {

}
