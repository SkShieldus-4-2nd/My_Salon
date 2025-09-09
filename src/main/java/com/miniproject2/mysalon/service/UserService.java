package com.miniproject2.mysalon.service;

import com.miniproject2.mysalon.controller.dto.UserDTO;
import com.miniproject2.mysalon.entity.User;
import com.miniproject2.mysalon.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;

    // 유저 생성
    public UserDTO.Response createUser(UserDTO.Request request) {
        User user = User.builder()
                .id(request.getId())
                .password(request.getPassword())
                .userName(request.getUserName())
                .profileImage(request.getProfileImage())
                .tall(request.getTall())
                .weight(request.getWeight())
                .type(request.getType())
                .storeName(request.getStoreName())
                .build();

        return UserDTO.Response.fromEntity(userRepository.save(user));
    }

    // 유저 수정
    public UserDTO.Response editUser(Long userNum, UserDTO.Request request) {
        User user = userRepository.findById(userNum)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setUserName(request.getUserName());
        user.setProfileImage(request.getProfileImage());
        user.setTall(request.getTall());
        user.setWeight(request.getWeight());
        user.setType(request.getType());
        user.setStoreName(request.getStoreName());

        return UserDTO.Response.fromEntity(userRepository.save(user));
    }

    // 유저 삭제
    public void deleteUser(Long userNum) {
        if (!userRepository.existsById(userNum)) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(userNum);
    }


    public List<UserDTO.Response> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserDTO.Response::fromEntity)
                .toList();
    }
}