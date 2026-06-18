package com.blog.dto;

public record LoginRequestDTO(
        String email,
        String password
) {
}