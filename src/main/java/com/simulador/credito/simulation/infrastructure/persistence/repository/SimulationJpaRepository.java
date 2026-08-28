package com.simulador.credito.simulation.infrastructure.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.simulador.credito.simulation.infrastructure.persistence.entity.SimulationEntity;

public interface SimulationJpaRepository extends JpaRepository<SimulationEntity, Long> {

    List<SimulationEntity> findAllByOrderByCreatedAtDesc();
}