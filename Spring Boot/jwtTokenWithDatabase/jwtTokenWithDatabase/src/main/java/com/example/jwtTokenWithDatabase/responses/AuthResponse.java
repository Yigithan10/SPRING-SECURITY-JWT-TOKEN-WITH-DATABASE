package com.example.jwtTokenWithDatabase.responses;

import com.example.jwtTokenWithDatabase.entities.User;
import lombok.Data;

@Data
public class AuthResponse {

    private String token;
    private User user;
}
