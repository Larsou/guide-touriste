package com.example.guide.controller;

import com.example.guide.dto.ReservationDTO;
import com.example.guide.dto.ReservationForm;
import com.example.guide.service.ReservationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reservations")
@Validated
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping
    public Page<ReservationDTO> list(Pageable pageable) {
        return reservationService.listAll(pageable);
    }

    @GetMapping("/{id}")
    public ReservationDTO getById(@PathVariable Long id) {
        return reservationService.getById(id);
    }

    @PostMapping
    public ReservationDTO create(
            @Valid @RequestBody ReservationForm form
    ) {
        return reservationService.create(form);
    }

    @PutMapping("/{id}")
    public ReservationDTO update(
            @PathVariable Long id,
            @Valid @RequestBody ReservationForm form
    ) {
        return reservationService.update(id, form);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        reservationService.delete(id);
    }
}