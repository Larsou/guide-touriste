package com.example.guide.controller;

import com.example.guide.dto.VilleDTO;
import com.example.guide.dto.VilleForm;
import com.example.guide.service.VilleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/villes")
@Validated
public class VilleController {

    @Autowired
    private VilleService villeService;

    // GET /api/villes?page=…&size=…
    @GetMapping
    public Page<VilleDTO> list(Pageable pageable) {
        return villeService.listAll(pageable);
    }

    // GET /api/villes/{id}
    @GetMapping("/{id}")
    public VilleDTO getById(@PathVariable Long id) {
        return villeService.getById(id);
    }

    // POST /api/villes
    @PostMapping
    public VilleDTO create(
            @Valid @RequestBody VilleForm form
    ) {
        return villeService.create(form);
    }

    // PUT /api/villes/{id}
    @PutMapping("/{id}")
    public VilleDTO update(
            @PathVariable Long id,
            @Valid @RequestBody VilleForm form
    ) {
        return villeService.update(id, form);
    }

    // DELETE /api/villes/{id}
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        villeService.delete(id);
    }
}
