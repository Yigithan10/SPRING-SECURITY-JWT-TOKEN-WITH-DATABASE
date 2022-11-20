package com.example.jwtTokenWithDatabase.services;

import org.springframework.stereotype.Service;

@Service
public class MessageService {
    public String messageJwt() {
        return "Hello this is my message with jwt token!";
    }
}
