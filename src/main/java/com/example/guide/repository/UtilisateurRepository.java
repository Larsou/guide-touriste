package com.example.guide.repository;

import com.example.guide.entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {

    /**
     * Recherche un Utilisateur par son email.
     * Spring Data JPA génèrera automatiquement la requête.
     */
    Optional<Utilisateur> findByEmail(String email);
}
