package com.example.GarageCarsavvyProject;
import com.example.GarageCarsavvyProject.Enums.TypeDeRole;
import com.example.GarageCarsavvyProject.Model.Garage;
import com.example.GarageCarsavvyProject.Model.Role;
import com.example.GarageCarsavvyProject.Model.Utilisateur;
import com.example.GarageCarsavvyProject.Repository.RoleRepository;
import com.example.GarageCarsavvyProject.Repository.UtilisateurRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.example.GarageCarsavvyProject.Model.Utilisateur.*;


@AllArgsConstructor
@SpringBootApplication
public class GarageCarsavvyProjectApplication  implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(GarageCarsavvyProjectApplication.class, args);
	}

	private final RoleRepository roleRepository;
	private final UtilisateurRepository utilisateurRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	public void run(String... args) throws Exception {
		createUtilisateurIfNotExists("admin", "bdaria259@gmail.com", "Admin", TypeDeRole.ADMINISTRATEUR);
	}

	private void createUtilisateurIfNotExists(String nom, String email, String password, TypeDeRole roleType) {
		if (utilisateurRepository.findByEmail(email).isEmpty()) {

			Role role = this.roleRepository.save(Role.builder()

					.libelle(roleType)
					.build());
			Garage garage = null;
			Utilisateur utilisateur = Utilisateur.builder()
					.actif(true)
					.nom(nom)
					.prenom("Admin")
					.email(email)
					.phone_number("0123456789")
					.adresse("Adresse inconnue")
					.mdp(passwordEncoder.encode(password))
					.role(role)
					.build();

			utilisateurRepository.save(utilisateur);
		}

	}
}


