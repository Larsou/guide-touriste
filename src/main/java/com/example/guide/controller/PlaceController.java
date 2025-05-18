package com.example.guide.controller;

import com.example.guide.dto.PlaceDTO;
import com.example.guide.dto.PlaceForm;
import com.example.guide.service.PlaceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/places")
@Validated
public class PlaceController {

    @Autowired
    private PlaceService placeService;

    @GetMapping
    public Page<PlaceDTO> list(
            @RequestParam(required = false) Long villeId,
            Pageable pageable
    ) {
        return placeService.listAll(villeId, pageable);
    }

    @GetMapping("/{id}")
    public PlaceDTO getById(@PathVariable Long id) {
        return placeService.getById(id);
    }

    @PostMapping
    public PlaceDTO create(
            @Valid @RequestBody PlaceForm form
    ) {
        return placeService.create(form);
    }

    @PutMapping("/{id}")
    public PlaceDTO update(
            @PathVariable Long id,
            @Valid @RequestBody PlaceForm form
    ) {
        return placeService.update(id, form);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        placeService.delete(id);
    }
}