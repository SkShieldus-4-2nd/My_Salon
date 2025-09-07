package com.miniproject2.mysalon.service;

import com.miniproject2.mysalon.controller.dto.UserDTO;
import com.miniproject2.mysalon.entity.User;
import com.miniproject2.mysalon.exception.BusinessException;
import com.miniproject2.mysalon.exception.ErrorCode;
import com.miniproject2.mysalon.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserDTO.Response createUser(UserDTO.Request request) {
        if (userRepository.existsById(request.getId())) {
            throw new BusinessException(ErrorCode.RESOURCE_DUPLICATE, "User","id",request.getId());
        }

        User user = User.builder()
                .id(request.getId())
                .userName(request.getUserName())
                .build();

        User savedUser = userRepository.save(user);
        return UserDTO.Response.fromEntity(savedUser);
    }

    public List<UserDTO.Response> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserDTO.Response::fromEntity)
                .toList();
    }
}
