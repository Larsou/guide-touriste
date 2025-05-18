package com.example.guide.service;

import com.example.guide.dto.PlaceDTO;
import com.example.guide.dto.PlaceForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PlaceService {
    Page<PlaceDTO> listAll(Long villeId, Pageable pageable);
    PlaceDTO getById(Long id);
    PlaceDTO create(PlaceForm form);
    PlaceDTO update(Long id, PlaceForm form);
    void delete(Long id);
}