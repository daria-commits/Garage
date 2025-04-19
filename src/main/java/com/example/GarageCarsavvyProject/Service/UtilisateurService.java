package com.example.GarageCarsavvyProject.Service;

import com.example.GarageCarsavvyProject.Model.Role;
import com.example.GarageCarsavvyProject.Model.Utilisateur;
import com.example.GarageCarsavvyProject.Repository.RoleRepository;
import com.example.GarageCarsavvyProject.Repository.UtilisateurRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class UtilisateurService implements UserDetailsService {
    private RoleRepository roleRepository;
    private UtilisateurRepository utilisateurRepository;
    private BCryptPasswordEncoder passwordEncoder;


    public void inscription(Utilisateur utilisateur) {

        if (!utilisateur.getEmail().contains("@")) {
            throw new RuntimeException("Votre mail invalide");
        }

        if (!utilisateur.getEmail().contains(".")) {
            throw new RuntimeException("Votre mail invalide");
        }

        final Optional<Utilisateur> utilisateurOptional = this.utilisateurRepository.findByEmail(utilisateur.getEmail());
        if (utilisateurOptional.isPresent()) {
            throw new RuntimeException("Votre mail est déjà utilisé");
        }

        final String mdpCrypte = this.passwordEncoder.encode(utilisateur.getMdp());
        utilisateur.setMdp(mdpCrypte);

        Role roleClient=this.roleRepository.findByLibelle(utilisateur.getRole().getLibelle());
        if (roleClient == null) {
            roleClient=new Role();
            roleClient.setLibelle(utilisateur.getRole().getLibelle());
            roleClient=this.roleRepository.save(roleClient);
        }
        utilisateur.setRole(roleClient);
        utilisateur.setActif(true);
        utilisateur = this.utilisateurRepository.save(utilisateur);

    }



    @Override
    public Utilisateur loadUserByUsername(final String username) throws UsernameNotFoundException {
        return this.utilisateurRepository
                .findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Aucun utilisateur ne corespond à cet identifiant"));
    }


}
