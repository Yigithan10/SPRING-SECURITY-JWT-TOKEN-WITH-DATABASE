package com.example.jwtTokenWithDatabase.repos;

import com.example.jwtTokenWithDatabase.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
