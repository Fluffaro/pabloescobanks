package com.java.pabloescobanks.service;

import com.java.pabloescobanks.entity.User;
import com.java.pabloescobanks.exception.AuthException;
import com.java.pabloescobanks.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    // ✅ Register a new user
    public User registerUser(String name, String username, String email, String password,
                             Date birthday, Integer mobile, Date date_joined) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new AuthException("Username already exists");
        }
        if (userRepository.findByEmail(email).isPresent()) {
            throw new AuthException("Email already exists");
        }

        User user = new User();
        user.setName(name);
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password)); // Encrypt password
        user.setBirthday(birthday);
        user.setMobile(mobile);
        user.setDate_joined(date_joined);
        user.setRole("USER"); // Default role

        return userRepository.save(user);
    }

    // ✅ Fetch user by username
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new AuthException("User not found"));
    }

    // ✅ Check if password matches
    public boolean passwordMatches(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    // ✅ Load user details for Spring Security
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findUserByUsername(username);
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole()) // Use single role
                .build();
    }

    // ✅ Return a user's role
    public String getRole(String username) {
        return findUserByUsername(username).getRole();
    }

    // ✅ Get all users (for admin)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // ✅ Get user by ID
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new AuthException("User not found"));
    }

    // ✅ Update user details (Only name, email, mobile, and password)
    public User updateUser(Long id, User updatedUser) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setName(updatedUser.getName());
                    user.setEmail(updatedUser.getEmail());
                    user.setMobile(updatedUser.getMobile());

                    // Only update password if a new one is provided
                    if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
                        user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
                    }

                    return userRepository.save(user);
                })
                .orElseThrow(() -> new AuthException("User not found"));
    }

    // ✅ Delete user (Prevent deleting last admin)
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AuthException("User not found"));

        // Prevent deleting the last admin account
        if ("ADMIN".equals(user.getRole()) && userRepository.findAll().stream().filter(u -> "ADMIN".equals(u.getRole())).count() <= 1) {
            throw new AuthException("Cannot delete the last admin!");
        }

        userRepository.deleteById(id);
    }
}
