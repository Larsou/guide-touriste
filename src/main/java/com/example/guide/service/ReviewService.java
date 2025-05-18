package com.example.guide.service;

import com.example.guide.dto.ReviewDTO;
import com.example.guide.dto.ReviewForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewService {
    Page<ReviewDTO> listByPlace(Long placeId, Pageable pageable);
    ReviewDTO getById(Long id);
    ReviewDTO create(Long userId, ReviewForm form);
    ReviewDTO update(Long userId, Long reviewId, ReviewForm form);
    void delete(Long userId, Long reviewId);

    // Admin
    Page<ReviewDTO> listAll(Pageable pageable);
    void moderate(Long reviewId);
}