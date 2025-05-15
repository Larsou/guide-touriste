package com.example.guide.controller;

import com.example.guide.entity.Utilisateur;
import com.example.guide.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.example.guide.security.JwtUtil;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UtilisateurRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * POST /auth/register
     * Enregistre un nouvel utilisateur en hashant son mot de passe.
     */
    @PostMapping("/register")
    public ResponseEntity<Utilisateur> register(@RequestBody Utilisateur user) {
        // Hash du mot de passe
        user.setMotDePasse(passwordEncoder.encode(user.getMotDePasse()));
        Utilisateur saved = userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    /**
     * POST /auth/login
     * Vérifie les identifiants et renvoie l'utilisateur si OK.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Utilisateur loginRequest) {
        Optional<Utilisateur> opt = userRepository.findByEmail(loginRequest.getEmail());
        if (opt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email inconnu");
        }
        Utilisateur user = opt.get();
        // Vérification du mot de passe
        if (!passwordEncoder.matches(loginRequest.getMotDePasse(), user.getMotDePasse())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Mot de passe invalide");
        }
        // Génération du token
        String token = jwtUtil.generateToken(user.getEmail());

        // Réponse JSON avec token et user
        return ResponseEntity.ok(Map.of(
                        "token", token,
                "user", user
        ));
    }
}
