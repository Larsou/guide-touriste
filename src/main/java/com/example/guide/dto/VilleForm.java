package com.example.guide.dto;

import jakarta.validation.constraints.NotBlank;

public class VilleForm {
    @NotBlank(message = "Le nom ne peut pas être vide")
    private String nom;

    private String description;

    // Getters / Setters
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}