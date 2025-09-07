package com.miniproject2.mysalon.controller;

import com.miniproject2.mysalon.controller.dto.UserDTO;
import com.miniproject2.mysalon.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDTO.Response>> getAllUsers() {
        List<UserDTO.Response> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PostMapping
    public ResponseEntity<UserDTO.Response> createUser(@Valid @RequestBody UserDTO.Request request) {

        UserDTO.Response createdUser = userService.createUser(request);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }
}
