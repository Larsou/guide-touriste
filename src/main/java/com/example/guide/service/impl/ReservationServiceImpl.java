package com.example.guide.service.impl;

import com.example.guide.dto.ReservationDTO;
import com.example.guide.dto.ReservationForm;
import com.example.guide.entity.Reservation;
import com.example.guide.entity.Utilisateur;
import com.example.guide.entity.Place;
import com.example.guide.repository.ReservationRepository;
import com.example.guide.repository.VilleRepository; // non nécessaire
import com.example.guide.repository.PlaceRepository;
import com.example.guide.repository.UtilisateurRepository;
import com.example.guide.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationRepository repo;

    @Autowired
    private UtilisateurRepository userRepo;

    @Autowired
    private PlaceRepository placeRepo;

    private ReservationDTO toDTO(Reservation r) {
        return new ReservationDTO(
                r.getId(),
                r.getDateReservation(),
                r.getUser().getId(),
                r.getPlace().getId()
        );
    }

    @Override
    public Page<ReservationDTO> listAll(Pageable pageable) {
        return repo.findAll(pageable).map(this::toDTO);
    }

    @Override
    public ReservationDTO getById(Long id) {
        return repo.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public ReservationDTO create(ReservationForm form) {
        Utilisateur u = userRepo.findById(form.getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Utilisateur inconnu"));
        Place p = placeRepo.findById(form.getPlaceId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Lieu inconnu"));
        Reservation r = new Reservation();
        r.setDateReservation(form.getDateReservation());
        r.setUser(u);
        r.setPlace(p);
        return toDTO(repo.save(r));
    }

    @Override
    public ReservationDTO update(Long id, ReservationForm form) {
        return repo.findById(id)
                .map(r -> {
                    Utilisateur u = userRepo.findById(form.getUserId())
                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Utilisateur inconnu"));
                    Place p = placeRepo.findById(form.getPlaceId())
                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Lieu inconnu"));
                    r.setDateReservation(form.getDateReservation());
                    r.setUser(u);
                    r.setPlace(p);
                    return toDTO(repo.save(r));
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