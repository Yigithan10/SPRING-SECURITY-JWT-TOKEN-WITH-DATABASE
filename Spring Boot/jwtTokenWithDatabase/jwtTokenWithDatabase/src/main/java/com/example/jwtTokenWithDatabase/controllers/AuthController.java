package com.example.jwtTokenWithDatabase.controllers;

import com.example.jwtTokenWithDatabase.entities.User;
import com.example.jwtTokenWithDatabase.requests.AuthRequest;
import com.example.jwtTokenWithDatabase.responses.AuthResponse;
import com.example.jwtTokenWithDatabase.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<User> registerOneUser(@RequestBody User user){
        return authService.registerOneUser(user);
    }

    @PostMapping("/login")
    @ResponseBody
    public AuthResponse loginOneUser(@RequestBody AuthRequest authRequest){
        return authService.loginOneUser(authRequest);
    }

    @PostMapping("/parser/{token}")
    public ResponseEntity<User> parserToken(@PathVariable String token){
        return AuthService.parserToken(token);
    }
}
