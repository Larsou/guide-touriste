package com.example.guide.service;

import com.example.guide.dto.ReservationDTO;
import com.example.guide.dto.ReservationForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReservationService {
    Page<ReservationDTO> listAll(Pageable pageable);
    ReservationDTO getById(Long id);
    ReservationDTO create(ReservationForm form);
    ReservationDTO update(Long id, ReservationForm form);
    void delete(Long id);
}