package com.example.jwtTokenWithDatabase.services;

import com.example.jwtTokenWithDatabase.entities.User;
import com.example.jwtTokenWithDatabase.repos.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    public UserRepository userRepository;

    public PasswordEncoder passwordEncoder;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getOneUser(Long userId) {
        return userRepository.findById(userId).orElse( null);
    }
}