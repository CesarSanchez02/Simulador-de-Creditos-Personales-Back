package com.simulador.credito.simulation.domain.repository;

import java.util.List;
import java.util.Optional;

import com.simulador.credito.simulation.domain.model.CreditSimulation;

public interface SimulationRepository {

    CreditSimulation save(CreditSimulation simulation);

    Optional<CreditSimulation> findById(Long id);

    List<CreditSimulation> findAll();
}