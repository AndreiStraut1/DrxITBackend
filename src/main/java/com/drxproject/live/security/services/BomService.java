package com.drxproject.live.security.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.drxproject.live.models.Bom;
import com.drxproject.live.repositories.BomRepository;

@Service
public class BomService {
    @Autowired
    private BomRepository bomRepository;

    public List<Bom> getAllBoms() {
        return bomRepository.findAll();
    }

    public Bom saveBom(Bom bom) {
        return bomRepository.save(bom);
    }
}
