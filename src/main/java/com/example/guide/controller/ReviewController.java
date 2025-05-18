package com.example.guide.controller;

import com.example.guide.dto.ReviewDTO;
import com.example.guide.dto.ReviewForm;
import com.example.guide.service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Validated
public class ReviewController {

    @Autowired private ReviewService reviewService;

    // Liste des avis d'un lieu (public)
    @GetMapping("/places/{placeId}/reviews")
    public Page<ReviewDTO> listByPlace(
            @PathVariable Long placeId,
            Pageable pageable
    ) {
        return reviewService.listByPlace(placeId, pageable);
    }

    // Création d'un avis (authentifié)
    @PostMapping("/places/{placeId}/reviews")
    @PreAuthorize("isAuthenticated()")
    public ReviewDTO create(
            @PathVariable Long placeId,
            @Valid @RequestBody ReviewForm form,
            @AuthenticationPrincipal UserDetails user
    ) {
        return reviewService.create(Long.parseLong(user.getUsername()), form);
    }

    // Modification d'un avis (seul auteur)
    @PutMapping("/api/reviews/{id}")
    @PreAuthorize("isAuthenticated()")
    public ReviewDTO update(
            @PathVariable Long id,
            @Valid @RequestBody ReviewForm form,
            @AuthenticationPrincipal UserDetails user
    ) {
        return reviewService.update(Long.parseLong(user.getUsername()), id, form);
    }

    // Suppression d'un avis (seul auteur)
    @DeleteMapping("/api/reviews/{id}")
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails user
    ) {
        reviewService.delete(Long.parseLong(user.getUsername()), id);
    }

    // Modération (admin)
    @GetMapping("/api/reviews")
    @PreAuthorize("hasRole('ADMIN')")
    public Page<ReviewDTO> listAll(Pageable pageable) {
        return reviewService.listAll(pageable);
    }

    @DeleteMapping("/api/reviews/{id}/admin")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void moderate(@PathVariable Long id) {
        reviewService.moderate(id);
    }
}