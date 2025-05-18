package com.example.guide.dto;

import java.time.LocalDateTime;

public record ReviewDTO(
        Long id,
        String commentaire,
        int note,
        Long placeId,
        Long auteurId,
        String auteurEmail,
        LocalDateTime dateCreation
) {}