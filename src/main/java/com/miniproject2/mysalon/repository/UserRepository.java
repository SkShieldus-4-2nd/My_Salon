package com.miniproject2.mysalon.repository;

import com.miniproject2.mysalon.entity.Order;
import com.miniproject2.mysalon.entity.User;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findById(String id);

    Optional<User> findById(Long userNum);
    boolean existsById(String id);
    boolean existsByUserNum(Long userNum);
    boolean existsByUserName(String userName);

}
