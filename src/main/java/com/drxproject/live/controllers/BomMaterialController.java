package com.drxproject.live.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.drxproject.live.security.services.BomMaterialService;
import com.drxproject.live.models.BomMaterial;

@RestController
@RequestMapping("/api/bom-material")
public class BomMaterialController {
    @Autowired
    private BomMaterialService bomMaterialService;

    @GetMapping
    public List<BomMaterial> getAllBomMaterials() {
        return bomMaterialService.getAllBomMaterials();
    }

    @PostMapping
    public BomMaterial saveBomMaterial(@RequestBody BomMaterial bomMaterial) {
        return bomMaterialService.saveBomMaterial(bomMaterial);
    }
}
