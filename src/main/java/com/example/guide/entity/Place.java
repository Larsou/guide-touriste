package com.example.guide.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "place")
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String adresse;

    @Column(length = 2000)
    private String description;

    // Relation many-to-one vers Ville
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ville_id")
    private Ville ville;

    public Place() {}

    // Getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Ville getVille() { return ville; }
    public void setVille(Ville ville) { this.ville = ville; }
}
