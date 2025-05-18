package com.example.guide.service.impl;

import com.example.guide.dto.PlaceDTO;
import com.example.guide.dto.PlaceForm;
import com.example.guide.entity.Place;
import com.example.guide.entity.Ville;
import com.example.guide.repository.PlaceRepository;
import com.example.guide.repository.VilleRepository;
import com.example.guide.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PlaceServiceImpl implements PlaceService {

    @Autowired
    private PlaceRepository placeRepo;

    @Autowired
    private VilleRepository villeRepo;

    private PlaceDTO toDTO(Place p) {
        return new PlaceDTO(
                p.getId(),
                p.getNom(),
                p.getAdresse(),
                p.getDescription(),
                p.getVille().getId()
        );
    }

    @Override
    public Page<PlaceDTO> listAll(Long villeId, Pageable pageable) {
        if (villeId != null) {
            return placeRepo.findAllByVilleId(villeId, pageable).map(this::toDTO);
        }
        return placeRepo.findAll(pageable).map(this::toDTO);
    }

    @Override
    public PlaceDTO getById(Long id) {
        return placeRepo.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public PlaceDTO create(PlaceForm form) {
        Ville v = villeRepo.findById(form.getVilleId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ville inconnue"));
        Place p = new Place();
        p.setNom(form.getNom());
        p.setAdresse(form.getAdresse());
        p.setDescription(form.getDescription());
        p.setVille(v);
        return toDTO(placeRepo.save(p));
    }

    @Override
    public PlaceDTO update(Long id, PlaceForm form) {
        return placeRepo.findById(id)
                .map(p -> {
                    Ville v = villeRepo.findById(form.getVilleId())
                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ville inconnue"));
                    p.setNom(form.getNom());
                    p.setAdresse(form.getAdresse());
                    p.setDescription(form.getDescription());
                    p.setVille(v);
                    return toDTO(placeRepo.save(p));
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public void delete(Long id) {
        if (!placeRepo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        placeRepo.deleteById(id);
    }
}