package com.example.guide.service;

import com.example.guide.dto.VilleDTO;
import com.example.guide.dto.VilleForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VilleService {
    Page<VilleDTO> listAll(Pageable pageable);
    VilleDTO getById(Long id);
    VilleDTO create(VilleForm form);
    VilleDTO update(Long id, VilleForm form);
    void delete(Long id);
}