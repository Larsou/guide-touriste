package com.example.guide.config;

import com.example.guide.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;                     // <-- import pour @Autowired
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    // ← Ici, on injecte le filtre JWT
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 1) API REST stateless
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // 2) Désactive CSRF
                .csrf(csrf -> csrf.disable())
                // 3) Désactive HTTP Basic & form-login
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)

                // ← On place notre filtre **avant** le filtre username/password standard
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)

                // 4) Ouverture de /auth/**, sécurisation du reste
                .authorizeHttpRequests(auth -> auth
                        // toujours accessible sans auth
                        .requestMatchers("/auth/**").permitAll()

                        // Lecture libre
                        .requestMatchers(HttpMethod.GET, "/api/villes", "/api/villes/**").permitAll()

                        // Écriture/Maj/Suppression pour tout utilisateur authentifié (à affiner plus tard)
                        .requestMatchers(HttpMethod.POST,   "/api/villes/**").authenticated()
                        .requestMatchers(HttpMethod.PUT,    "/api/villes/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/villes/**").authenticated()

                        // Places…
                        .requestMatchers(HttpMethod.GET, "/api/places", "/api/places/**").permitAll()

                        .requestMatchers(HttpMethod.POST,   "/api/places/**").authenticated()
                        .requestMatchers(HttpMethod.PUT,    "/api/places/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/places/**").authenticated()



                        // Toutes les autres requêtes nécessitent authentification
                        .anyRequest().authenticated()
                );

        return http.build();
    }
}
