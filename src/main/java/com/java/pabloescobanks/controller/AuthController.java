package com.java.pabloescobanks.controller;

import com.java.pabloescobanks.entity.User;
import com.java.pabloescobanks.exception.AuthException;
import com.java.pabloescobanks.service.UserService;
import com.java.pabloescobanks.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        User registeredUser = userService.registerUser(
                user.getName(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getBirthday(),
                user.getMobile(),
                user.getDate_joined()
        );

        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        User existingUser = userService.findUserByUsername(user.getUsername());

        if (!userService.passwordMatches(user.getPassword(), existingUser.getPassword())) {
            throw new AuthException("Invalid Credentials");
        }

        String role = existingUser.getRole(); // Fetch role as a single string
        String token = jwtUtil.generateToken(existingUser.getUsername(), existingUser.getRole());

        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        return ResponseEntity.ok(response);
    }
}
