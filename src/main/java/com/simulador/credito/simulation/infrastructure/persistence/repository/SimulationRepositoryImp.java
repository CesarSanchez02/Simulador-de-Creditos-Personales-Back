package com.simulador.credito.simulation.infrastructure.persistence.repository;

import java.util.Optional;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.simulador.credito.clients.infrastructure.persistence.repository.ClientJpaRepository;
import com.simulador.credito.simulation.domain.model.CreditSimulation;
import com.simulador.credito.simulation.domain.repository.SimulationRepository;
import com.simulador.credito.simulation.infrastructure.persistence.entity.SimulationEntity;

@Repository
public class SimulationRepositoryImp implements SimulationRepository {

    private final SimulationJpaRepository simulationJpaRepository;
    private final ClientJpaRepository clientJpaRepository;

    public SimulationRepositoryImp(SimulationJpaRepository simulationJpaRepository,
            ClientJpaRepository clientJpaRepository) {
        this.simulationJpaRepository = simulationJpaRepository;
        this.clientJpaRepository = clientJpaRepository;
    }

    @Override
    public CreditSimulation save(CreditSimulation simulation) {
        SimulationEntity entity = new SimulationEntity();
        entity.setClient(clientJpaRepository.getReferenceById(simulation.getClientId()));
        entity.setRequestedAmount(simulation.getRequestedAmount());
        entity.setAnnualInterestRate(simulation.getAnnualInterestRate());
        entity.setTermInMonths(simulation.getTermInMonths());
        entity.setMonthlyInterestRate(simulation.getMonthlyInterestRate());
        entity.setMonthlyPayment(simulation.getMonthlyPayment());
        entity.setTotalPayment(simulation.getTotalPayment());
        entity.setTotalInterest(simulation.getTotalInterest());

        SimulationEntity saved = simulationJpaRepository.save(entity);
        return new CreditSimulation(
                saved.getId(),
                saved.getClient().getId(),
                saved.getClient().getFullName(),
                saved.getRequestedAmount(), saved.getAnnualInterestRate(),
                saved.getTermInMonths(), saved.getMonthlyInterestRate(),
                saved.getMonthlyPayment(), saved.getTotalPayment(),
                saved.getTotalInterest(), saved.getCreatedAt());
    }

    @Override
    public Optional<CreditSimulation> findById(Long id) {
        return simulationJpaRepository.findById(id).map(saved -> new CreditSimulation(
                saved.getId(), saved.getClient().getId(), saved.getClient().getFullName(),
                saved.getRequestedAmount(), saved.getAnnualInterestRate(), saved.getTermInMonths(),
                saved.getMonthlyInterestRate(), saved.getMonthlyPayment(),
                saved.getTotalPayment(), saved.getTotalInterest(), saved.getCreatedAt()));
    }

    @Override
    public List<CreditSimulation> findAll() {
        return simulationJpaRepository.findAllByOrderByCreatedAtDesc().stream()
                .map(saved -> new CreditSimulation(
                        saved.getId(), saved.getClient().getId(), saved.getClient().getFullName(),
                        saved.getRequestedAmount(), saved.getAnnualInterestRate(), saved.getTermInMonths(),
                        saved.getMonthlyInterestRate(), saved.getMonthlyPayment(),
                        saved.getTotalPayment(), saved.getTotalInterest(), saved.getCreatedAt()))
                .toList();
    }
}