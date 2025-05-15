package com.example.guide.repository;

import com.example.guide.entity.Place;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepository extends JpaRepository<Place, Long> {
    // Méthode de filtrage par ville + pagination
    Page<Place> findAllByVilleId(Long villeId, Pageable pageable);
}
