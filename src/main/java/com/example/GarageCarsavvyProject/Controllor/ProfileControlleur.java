package com.example.GarageCarsavvyProject.Controllor;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ProfileControlleur {
    @GetMapping("/profile")
    public Object getCurrentUser() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = "";

        if (authentication != null && authentication.isAuthenticated()) {
            final Object principal = authentication.getPrincipal();
            return principal;

        }

        return null;
    }
}
