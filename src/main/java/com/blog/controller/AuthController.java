package com.blog.controller;

import com.blog.dto.LoginRequestDTO;
import com.blog.model.User;
import com.blog.service.JwtService;
import com.blog.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService, JwtService jwt) {
        this.userService = userService;
    }

    @GetMapping("/hello")
    public ResponseEntity<?> hello() {
        return ResponseEntity.ok().body("Hello World from API! - NOT AUTHENTICATE");
    }

    @PostMapping("/register")
    public ResponseEntity<User> create(@RequestBody User user) {
        return ResponseEntity.ok(userService.create(user));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO request) {
        return ResponseEntity.ok(userService.login(request.email(), request.password()).getBody());
    }
}
