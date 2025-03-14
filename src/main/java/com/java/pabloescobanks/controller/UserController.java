package com.java.pabloescobanks.controller;

import com.java.pabloescobanks.entity.User;
import com.java.pabloescobanks.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Get all users (Admin only)
    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Get user by ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);

        User temporaryUser = new User();
        temporaryUser.setName(userRepository.findById(id).get().getName());
        temporaryUser.setEmail(userRepository.findById(id).get().getEmail());
        temporaryUser.setMobile(userRepository.findById(id).get().getMobile());
        temporaryUser.setBirthday(userRepository.findById(id).get().getBirthday());
        temporaryUser.setRole(userRepository.findById(id).get().getRole());
        temporaryUser.setUsername(userRepository.findById(id).get().getUsername());
        temporaryUser.setDate_joined(userRepository.findById(id).get().getDate_joined());

        return ResponseEntity.ok(temporaryUser);
    }

    // Update user details
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setName(updatedUser.getName());
                    user.setEmail(updatedUser.getEmail());
                    user.setMobile(updatedUser.getMobile());
                    return ResponseEntity.ok(userRepository.save(user));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Delete user
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return ResponseEntity.ok("User deleted successfully.");
        }
        return ResponseEntity.notFound().build();
    }


}
