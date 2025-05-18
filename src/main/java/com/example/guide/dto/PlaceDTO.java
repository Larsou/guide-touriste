package com.example.guide.dto;

public record PlaceDTO(
        Long id,
        String nom,
        String adresse,
        String description,
        Long villeId
) {}