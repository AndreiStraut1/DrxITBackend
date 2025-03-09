package com.drxproject.live.controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.drxproject.live.models.ERole;
import com.drxproject.live.models.Role;
import com.drxproject.live.models.User;
import com.drxproject.live.repositories.RoleRepository;
import com.drxproject.live.repositories.UserRepository;
import com.drxproject.live.security.services.UserDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllUsers() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Long currentUserId = userDetails.getId();
        Optional<User> userOpt = userRepository.findById(currentUserId);
        List<User> users = new ArrayList<User>();

        if (userOpt.isPresent()) {
            User user = userOpt.get();

            if (userHasRole(user, ERole.ROLE_ADMIN)) {
                try {
                    users = userRepository.findAll();

                    users = users.stream()
                            .filter(u -> !u.getId().equals(currentUserId))
                            .collect(Collectors.toList());

                    return ResponseEntity.ok(users);
                } catch (Exception e) {
                    return ResponseEntity.badRequest().body(e.getMessage());
                }
            } else {
                return ResponseEntity.badRequest().body("User is not authorized to view users");
            }
        }

        if (users.isEmpty()) {
            return ResponseEntity.badRequest().body("User does not have permission to see users.");
        } else {
            return ResponseEntity.ok(users);
        }
    }

    @PutMapping("/update-role/{userId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateRole(@PathVariable Long userId, @RequestBody List<String> userRoles) {
        System.out.println("Testing");
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            Set<Role> roles = new HashSet<>();
            for (String roleName : userRoles) {
                Role role = roleRepository.findByName(ERole.valueOf(roleName))
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(role);
            }
            user.setRoles(roles);
            userRepository.save(user);
            return ResponseEntity.ok("User roles updated successfully.");
        } else {
            return ResponseEntity.badRequest().body("User not found.");
        }
    }

    @DeleteMapping("/delete/{userId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            userRepository.deleteById(userId);
            return ResponseEntity.ok("User deleted successfully.");
        } else {
            return ResponseEntity.badRequest().body("User not found.");
        }
    }

    private boolean userHasRole(User user, ERole role) {
        return user.getRoles().stream().anyMatch(r -> r.getName().equals(role));
    }
}
