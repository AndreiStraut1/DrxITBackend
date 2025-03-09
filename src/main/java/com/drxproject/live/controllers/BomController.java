package com.drxproject.live.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.drxproject.live.security.services.BomService;
import com.drxproject.live.models.Bom;

@RestController
@RequestMapping("/api/bom")
public class BomController {
    @Autowired
    private BomService bomService;

    @GetMapping
    public List<Bom> getAllBoms() {
        return bomService.getAllBoms();
    }

    @PostMapping
    public Bom saveBom(Bom bom) {
        return bomService.saveBom(bom);
    }

}
