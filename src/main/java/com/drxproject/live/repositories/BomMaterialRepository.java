package com.drxproject.live.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.drxproject.live.models.BomMaterial;

@Repository
public interface BomMaterialRepository extends JpaRepository<BomMaterial, Integer> {

}
