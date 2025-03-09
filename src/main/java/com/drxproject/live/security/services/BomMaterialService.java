package com.drxproject.live.security.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.drxproject.live.models.BomMaterial;
import com.drxproject.live.repositories.BomMaterialRepository;

@Service
public class BomMaterialService {
    @Autowired
    private BomMaterialRepository bomMaterialRepository;

    public List<BomMaterial> getAllBomMaterials() {
        return bomMaterialRepository.findAll();
    }

    public BomMaterial saveBomMaterial(BomMaterial bomMaterial) {
        return bomMaterialRepository.save(bomMaterial);
    }
}
