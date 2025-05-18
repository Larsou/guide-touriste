package com.example.guide.repository;

import com.example.guide.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    // (optionnel) Page<Reservation> findAllByUserId(Long userId, Pageable p);
    // (optionnel) Page<Reservation> findAllByPlaceId(Long placeId, Pageable p);
}