package com.example.guide.service.impl;

import com.example.guide.dto.ReviewDTO;
import com.example.guide.dto.ReviewForm;
import com.example.guide.entity.Place;
import com.example.guide.entity.Review;
import com.example.guide.entity.Utilisateur;
import com.example.guide.repository.PlaceRepository;
import com.example.guide.repository.ReviewRepository;
import com.example.guide.repository.UtilisateurRepository;
import com.example.guide.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired private ReviewRepository reviewRepo;
    @Autowired private PlaceRepository placeRepo;
    @Autowired private UtilisateurRepository userRepo;

    private ReviewDTO toDTO(Review r) {
        return new ReviewDTO(
                r.getId(),
                r.getCommentaire(),
                r.getNote(),
                r.getPlace().getId(),
                r.getAuteur().getId(),
                r.getAuteur().getEmail(),
                r.getDateCreation()
        );
    }

    @Override
    public Page<ReviewDTO> listByPlace(Long placeId, Pageable pageable) {
        return reviewRepo.findAllByPlaceId(placeId, pageable).map(this::toDTO);
    }

    @Override
    public Page<ReviewDTO> listAll(Pageable pageable) {
        return reviewRepo.findAll(pageable).map(this::toDTO);
    }

    @Override
    public ReviewDTO getById(Long id) {
        return reviewRepo.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public ReviewDTO create(Long userId, ReviewForm form) {
        Utilisateur u = userRepo.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Utilisateur inconnu"));
        Place p = placeRepo.findById(form.getPlaceId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Lieu inconnu"));
        Review r = new Review();
        r.setCommentaire(form.getCommentaire());
        r.setNote(form.getNote());
        r.setPlace(p);
        r.setAuteur(u);
        return toDTO(reviewRepo.save(r));
    }

    @Override
    public ReviewDTO update(Long userId, Long reviewId, ReviewForm form) {
        return reviewRepo.findById(reviewId)
                .map(r -> {
                    if (!r.getAuteur().getId().equals(userId)) {
                        throw new ResponseStatusException(HttpStatus.FORBIDDEN);
                    }
                    r.setCommentaire(form.getCommentaire());
                    r.setNote(form.getNote());
                    return toDTO(reviewRepo.save(r));
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public void delete(Long userId, Long reviewId) {
        Review r = reviewRepo.findById(reviewId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!r.getAuteur().getId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        reviewRepo.deleteById(reviewId);
    }

    @Override
    public void moderate(Long reviewId) {
        if (!reviewRepo.existsById(reviewId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        reviewRepo.deleteById(reviewId);
    }
}