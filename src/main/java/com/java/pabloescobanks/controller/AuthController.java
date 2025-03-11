package com.java.pabloescobanks.controller;

import com.java.pabloescobanks.entity.User;
import com.java.pabloescobanks.exception.AuthException;
import com.java.pabloescobanks.service.AccountService;
import com.java.pabloescobanks.service.UserService;
import com.java.pabloescobanks.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.java.pabloescobanks.service.AccountService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final AccountService accountService;

    public AuthController(UserService userService, JwtUtil jwtUtil, AccountService accountService) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.accountService = accountService;
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
        accountService.createAccount(registeredUser.getUId());
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        // Use the unified service method to find by username or email.
        User existingUser = userService.findUserByUsernameOrEmail(user.getUsername());

        // Validate password
        if (!userService.passwordMatches(user.getPassword(), existingUser.getPassword())) {
            throw new AuthException("Invalid Credentials");
        }

        String token = jwtUtil.generateToken(existingUser.getUsername(), existingUser.getRole());
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        response.put("userId", existingUser.getUId().toString()); // Added userId in response

        return ResponseEntity.ok(response);
    }


}
