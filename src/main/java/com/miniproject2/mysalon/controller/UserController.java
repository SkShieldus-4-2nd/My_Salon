package com.miniproject2.mysalon.controller;

import com.miniproject2.mysalon.controller.dto.UserDTO;
import com.miniproject2.mysalon.security.CurrentUser;
import com.miniproject2.mysalon.security.jwt.JwtService;
import com.miniproject2.mysalon.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    // ✅ 유저 생성
    @PostMapping
    public ResponseEntity<UserDTO.Response> createUser(@Valid @RequestBody UserDTO.Request request) {
        return ResponseEntity.ok(userService.createUser(request));
    }

    // ✅ 유저 수정
    @PutMapping("/edit-profile")
    public ResponseEntity<UserDTO.Response> editUser(
            @CurrentUser Long userNum,
            @Valid @RequestBody UserDTO.Request request
    ) {
        return ResponseEntity.ok(userService.editUser(userNum, request));
    }

    // ✅ 유저 삭제
    @DeleteMapping("/{userNum}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userNum) {
        userService.deleteUser(userNum);
        return ResponseEntity.noContent().build();
    }

    // ✅ 모든 유저 조회
    @GetMapping
    public ResponseEntity<List<UserDTO.Response>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // ✅ 유저 넘으로 유저 조회
    @GetMapping("/{userNum}")
    public ResponseEntity<UserDTO.Response> getUserByUserNum(@PathVariable Long userNum) {
        UserDTO.Response userResponse = userService.getUserByUserNum(userNum);
        return ResponseEntity.ok(userResponse);
    }

    // ✅ 로그인한 유저의 정보 조회
    @GetMapping("/user-info")
    public ResponseEntity<UserDTO.Response> getUserInfo(@CurrentUser Long userNum) {
        UserDTO.Response userResponse = userService.getUserByUserNum(userNum);
        return ResponseEntity.ok(userResponse);
    }

    //jwt 토큰 생성
    @PostMapping("/login")
    public ResponseEntity<UserDTO.LoginResponse>  authenticateAndGetToken(@RequestBody UserDTO.LoginRequest authRequest) {
        UserDTO.LoginResponse userResponse = userService.login(authRequest);
        return ResponseEntity.ok(userResponse);
    }
}
