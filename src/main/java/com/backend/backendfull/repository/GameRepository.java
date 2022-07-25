package com.backend.backendfull.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.backendfull.model.Game;

@Repository
public interface GameRepository extends JpaRepository<Game, Integer> {

}
