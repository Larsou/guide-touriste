package com.example.guide;

import com.example.guide.entity.Utilisateur;
import com.example.guide.repository.UtilisateurRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GuideApplication {
	public static void main(String[] args) {
		SpringApplication.run(GuideApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(UtilisateurRepository repo) {
		return args -> {
			Utilisateur u = new Utilisateur();
			u.setNom("TestUser");
			u.setEmail("test@example.com");
			u.setMotDePasse("1234");
			repo.save(u);
			System.out.println("Nombre d'utilisateurs en base : " + repo.findAll().size());
		};
	}
}
