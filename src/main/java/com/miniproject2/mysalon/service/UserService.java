package com.miniproject2.mysalon.service;

import com.miniproject2.mysalon.controller.dto.UserDTO;
import com.miniproject2.mysalon.entity.User;
import com.miniproject2.mysalon.exception.BusinessException;
import com.miniproject2.mysalon.exception.ErrorCode;
import com.miniproject2.mysalon.repository.UserRepository;

import com.miniproject2.mysalon.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    // 로그인
    @Transactional(readOnly = true)
    public UserDTO.LoginResponse login(UserDTO.LoginRequest request) {
        User user = userRepository.findById(request.getId())
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BusinessException(ErrorCode.AUTH_INVALID_CREDENTIALS);
        }

        String token = jwtUtil.generateToken(user.getId(), user.getUserNum());

        return UserDTO.LoginResponse.builder()
                .token(token)
                .user(UserDTO.Response.fromEntity(user))
                .build();
    }

    // 유저 생성
    public UserDTO.Response createUser(UserDTO.Request request) {
        if (userRepository.existsById(request.getId())) {
            throw new BusinessException(ErrorCode.USER_ID_DUPLICATE);
        }
        if (userRepository.existsByUserName(request.getUserName())) {
            throw new BusinessException(ErrorCode.USER_NAME_DUPLICATE);
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        String encodedSecondPassword = passwordEncoder.encode(request.getSecondPassword());

        User user = User.builder()
                .id(request.getId())
                .password(encodedPassword)
                .userName(request.getUserName())
                .secondPassword(encodedSecondPassword)
                .gender(request.getGender())
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
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        user.setUserName(request.getUserName());
        user.setProfileImage(request.getProfileImage());
        user.setTall(request.getTall());
        user.setWeight(request.getWeight());
        user.setType(request.getType());
        user.setStoreName(request.getStoreName());

        // 비밀번호가 null이 아니면 암호화 후 업데이트
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        if (request.getSecondPassword() != null && !request.getSecondPassword().isEmpty()) {
            user.setSecondPassword(passwordEncoder.encode(request.getSecondPassword()));
        }

        return UserDTO.Response.fromEntity(userRepository.save(user));
    }

    // 유저 삭제
    public void deleteUser(Long userNum) {
        if (!userRepository.existsById(userNum)) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND);
        }
        userRepository.deleteById(userNum);
    }


    public List<UserDTO.Response> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserDTO.Response::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public UserDTO.Response getUserByUserNum(Long userNum) {
        User user = userRepository.findById(userNum)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        return UserDTO.Response.fromEntity(user);
    }

}