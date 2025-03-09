package com.drxproject.live.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.drxproject.live.models.Material;

@Repository
public interface MaterialRepository extends JpaRepository<Material, String> {
    @SuppressWarnings("null")
    Optional<Material> findById(String id);
}
