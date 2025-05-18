package com.example.guide.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PlaceForm {
    @NotBlank(message = "Le nom ne peut pas être vide")
    private String nom;

    @NotBlank(message = "L’adresse ne peut pas être vide")
    private String adresse;

    private String description;

    @NotNull(message = "Vous devez préciser l’ID de la ville")
    private Long villeId;

    // getters & setters...
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Long getVilleId() { return villeId; }
    public void setVilleId(Long villeId) { this.villeId = villeId; }
}