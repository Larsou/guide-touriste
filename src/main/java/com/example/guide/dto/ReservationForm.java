package com.example.guide.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class ReservationForm {
    @NotNull(message = "La date de réservation est obligatoire")
    private LocalDateTime dateReservation;

    @NotNull(message = "L'ID utilisateur est obligatoire")
    private Long userId;

    @NotNull(message = "L'ID du lieu est obligatoire")
    private Long placeId;

    // getters / setters
    public LocalDateTime getDateReservation() { return dateReservation; }
    public void setDateReservation(LocalDateTime dateReservation) { this.dateReservation = dateReservation; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getPlaceId() { return placeId; }
    public void setPlaceId(Long placeId) { this.placeId = placeId; }
}