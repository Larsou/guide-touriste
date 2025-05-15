package com.example.guide.controller;

import com.example.guide.entity.Place;
import com.example.guide.repository.PlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Si vous consommez depuis http://localhost:3000, décommentez la ligne ci-dessous :
//@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/places")


public class PlaceController {
    @Autowired
    private PlaceRepository placeRepo;

    // GET /api/places[?villeId=…] + pagination
    @GetMapping
    public Page<Place> list(
            @RequestParam(required = false) Long villeId,
            Pageable pageable
    ) {
        if (villeId != null) {
            return placeRepo.findAllByVilleId(villeId, pageable);
        }
        return placeRepo.findAll(pageable);
    }

    // GET /api/places/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Place> getById(@PathVariable Long id) {
        return placeRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/places
    @PostMapping
    public Place create(@RequestBody Place p) {
        return placeRepo.save(p);
    }

    // PUT /api/places/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Place> update(
            @PathVariable Long id,
            @RequestBody Place placeReq
    ) {
        return placeRepo.findById(id)
                .map(p -> {
                    p.setNom(placeReq.getNom());
                    p.setAdresse(placeReq.getAdresse());
                    p.setDescription(placeReq.getDescription());
                    p.setVille(placeReq.getVille());
                    return ResponseEntity.ok(placeRepo.save(p));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/places/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!placeRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        placeRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
