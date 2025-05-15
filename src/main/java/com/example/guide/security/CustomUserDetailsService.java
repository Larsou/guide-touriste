package com.example.guide.security;

import com.example.guide.entity.Utilisateur;
import com.example.guide.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UtilisateurRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Utilisateur user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé avec l’email : " + email));
        // ici on affecte un rôle simple ROLE_USER ; vous pourrez enrichir plus tard
        return User.withUsername(user.getEmail())
                .password(user.getMotDePasse())
                .authorities("ROLE_USER")
                .build();
    }
}
