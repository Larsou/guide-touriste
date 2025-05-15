package com.example.guide.repository;

import com.example.guide.entity.Ville;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VilleRepository extends JpaRepository<Ville, Long> {
}
