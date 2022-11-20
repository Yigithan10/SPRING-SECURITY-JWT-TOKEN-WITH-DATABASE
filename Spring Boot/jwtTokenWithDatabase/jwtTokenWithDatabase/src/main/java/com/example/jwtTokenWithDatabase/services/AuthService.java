package com.example.jwtTokenWithDatabase.services;

import com.example.jwtTokenWithDatabase.entities.User;
import com.example.jwtTokenWithDatabase.exceptions.AuthExeption;
import com.example.jwtTokenWithDatabase.jwt.JwtUtil;
import com.example.jwtTokenWithDatabase.repos.UserRepository;
import com.example.jwtTokenWithDatabase.requests.AuthRequest;
import com.example.jwtTokenWithDatabase.responses.AuthResponse;
import com.example.jwtTokenWithDatabase.security.CustomUserDetailsService;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    public static UserRepository userRepository;

    public PasswordEncoder passwordEncoder;

    private static final String secretKey = "my-app-secret";

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public ResponseEntity<User> registerOneUser(User user) {
        if(userRepository.findByUsername(user.getUsername().toLowerCase())!=null){
            return new ResponseEntity<>(user, HttpStatus.BAD_REQUEST);
        }else {
            User newUser = new User();
            newUser.setUsername(user.getUsername().toLowerCase());
            newUser.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(newUser);
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        }
    }

    public AuthResponse loginOneUser(AuthRequest authRequest) {
        User foundUser = userRepository.findByUsername(authRequest.getUsername());
        if(foundUser==null){
            throw new AuthExeption();
        }
        boolean matches =  passwordEncoder.matches(authRequest.getPassword(), foundUser.getPassword());
        if(matches){
            User user = foundUser;
            try {
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
            } catch (BadCredentialsException ex) {
                throw new BadCredentialsException("Incorret ", ex);
            }
            final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
            final String token = jwtUtil.generateToken(userDetails);
            AuthResponse authResponse = new AuthResponse();
            authResponse.setUser(user);
            authResponse.setToken(token);
            return authResponse;
        }else {
            throw new AuthExeption();
        }
    }

    public static ResponseEntity<User> parserToken(String token) {
        JwtParser parser = Jwts.parser().setSigningKey(secretKey);
        try{
            parser.parse(token);
            Claims claims = parser.parseClaimsJws(token).getBody();
            Long userId = Long.valueOf(claims.getSubject());
            Optional<User> user = userRepository.findById(userId);
            return new ResponseEntity(user, HttpStatus.OK);
        } catch (ExpiredJwtException e) {
            System.out.println(" Token expired ");
            User user = new User();
            return new ResponseEntity(user, HttpStatus.BAD_GATEWAY);
        } catch (SignatureException e) {
            System.out.println(" s ");
            User user = new User();
            return new ResponseEntity(user, HttpStatus.BAD_REQUEST);
        } catch(Exception e){
            System.out.println(" e ");
            User user = new User();
            return new ResponseEntity(user, HttpStatus.BAD_REQUEST);
        }
    }
}
