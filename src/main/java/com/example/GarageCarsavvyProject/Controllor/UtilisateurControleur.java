package com.example.GarageCarsavvyProject.Controllor;

import com.example.GarageCarsavvyProject.DTO.AuthentificationDTO;
import com.example.GarageCarsavvyProject.Model.Utilisateur;
import com.example.GarageCarsavvyProject.Security.JwtService;
import com.example.GarageCarsavvyProject.Service.UtilisateurService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
public class UtilisateurControleur {
    private JwtService jwtService;
    private AuthenticationManager authenticationManager;
    private UtilisateurService utilisateurService;


    @PostMapping(path = "inscription")
    public void inscription(@RequestBody Utilisateur utilisateur) {
        this.utilisateurService.inscription(utilisateur);
    }

    @PostMapping(path = "deconnexion")
    public void deconnexion() {
        this.jwtService.deconnexion();
    }

    @PostMapping(path = "login")
    public Map<String, String> connexion(@RequestBody AuthentificationDTO authentificationDTO) {
        final Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authentificationDTO.username(),
                        authentificationDTO.password())
        );
        if (authenticate.isAuthenticated()) {
            return this.jwtService.generate(authentificationDTO.username());
        }
        return null;
    }
}

