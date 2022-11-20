package com.example.jwtTokenWithDatabase.controllers;

import com.example.jwtTokenWithDatabase.entities.User;
import com.example.jwtTokenWithDatabase.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    public List<User> allUsers = new ArrayList<>();
    public UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers(){
        allUsers.clear();
        allUsers.addAll(userService.getAllUsers());
        return userService.getAllUsers();
    }

    @GetMapping("/{userId}")
    public User getOneUser(@PathVariable Long userId){
        return userService.getOneUser(userId);
    }
}
