package com.drxproject.live.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.drxproject.live.models.EStage;
import com.drxproject.live.models.Stage;

@Repository
public interface StageRepository extends JpaRepository<Stage, Integer> {
    Optional<Stage> findByName(EStage name);
}