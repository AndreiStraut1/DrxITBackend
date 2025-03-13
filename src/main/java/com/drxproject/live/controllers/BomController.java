package com.drxproject.live.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.drxproject.live.security.services.BomService;
import com.drxproject.live.models.Bom;
import com.drxproject.live.repositories.BomRepository;

@RestController
@RequestMapping("/api/bom")
public class BomController {
    @Autowired
    private BomService bomService;

    @Autowired
    private BomRepository bomRepository;

    @GetMapping
    public List<Bom> getAllBoms() {
        return bomService.getAllBoms();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBomById(@PathVariable Long id) {
        Optional<Bom> bom = bomRepository.findById(id);
        if (bom.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(bom);
    }

    @PostMapping
    public Bom saveBom(Bom bom) {
        return bomService.saveBom(bom);
    }

}
