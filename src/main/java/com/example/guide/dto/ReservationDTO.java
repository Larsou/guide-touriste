package com.example.guide.dto;

import java.time.LocalDateTime;

public record ReservationDTO(
        Long id,
        LocalDateTime dateReservation,
        Long userId,
        Long placeId
) {}