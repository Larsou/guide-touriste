package com.example.guide.service.impl;

import com.example.guide.dto.VilleDTO;
import com.example.guide.dto.VilleForm;
import com.example.guide.entity.Ville;
import com.example.guide.repository.VilleRepository;
import com.example.guide.service.VilleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class VilleServiceImpl implements VilleService {

    @Autowired
    private VilleRepository repo;

    private VilleDTO toDTO(Ville v) {
        return new VilleDTO(v.getId(), v.getNom(), v.getDescription());
    }

    @Override
    public Page<VilleDTO> listAll(Pageable pageable) {
        return repo.findAll(pageable).map(this::toDTO);
    }

    @Override
    public VilleDTO getById(Long id) {
        return repo.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public VilleDTO create(VilleForm form) {
        Ville v = new Ville();
        v.setNom(form.getNom());
        v.setDescription(form.getDescription());
        return toDTO(repo.save(v));
    }

    @Override
    public VilleDTO update(Long id, VilleForm form) {
        return repo.findById(id)
                .map(v -> {
                    v.setNom(form.getNom());
                    v.setDescription(form.getDescription());
                    return toDTO(repo.save(v));
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        repo.deleteById(id);
    }
}