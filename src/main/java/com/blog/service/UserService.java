package com.blog.service;

import com.blog.model.User;
import com.blog.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwt;

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder, JwtService jwt) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwt = jwt;
    }

    public User create(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repository.save(user);
    }

    public ResponseEntity<?> login(String email, String password) {
        User user = repository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found!"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            return ResponseEntity.status(401).body("Invalid Password!");
        }

        String token = jwt.generateToken(user.getId().toString());

        return ResponseEntity.ok(token);
    }
}
