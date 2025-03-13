package com.drxproject.live.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.drxproject.live.models.Bom;

@Repository
public interface BomRepository extends JpaRepository<Bom, Integer> {
    Optional<Bom> findById(Long id);
}
