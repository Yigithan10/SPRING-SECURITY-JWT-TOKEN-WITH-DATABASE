package com.example.jwtTokenWithDatabase.entities;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name="users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="user_id", nullable=false, unique=true)
    private Long id;

    @Column(name="user_username", nullable=false, unique=false)
    private String username;

    @Column(name="user_password", nullable=false, unique=false)
    private String password;

    @Column(name="user_time", nullable = false, unique = false)
    LocalTime localTime = LocalTime.now();

    @Column(name="user_date", nullable = false, unique = false)
    LocalDate localDate = LocalDate.now();
}
