package com.simulador.credito.installment.infrastructure.persistence.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.simulador.credito.installment.domain.model.Installment;
import com.simulador.credito.installment.domain.repository.InstallmentRepository;
import com.simulador.credito.installment.infrastructure.persistence.entity.InstallmentEntity;
import com.simulador.credito.simulation.infrastructure.persistence.repository.SimulationJpaRepository;

@Repository
public class InstallmentRepositoryImp implements InstallmentRepository {

    private final InstallmentJpaRepository installmentJpaRepository;
    private final SimulationJpaRepository simulationJpaRepository;

    public InstallmentRepositoryImp(InstallmentJpaRepository installmentJpaRepository,
            SimulationJpaRepository simulationJpaRepository) {
        this.installmentJpaRepository = installmentJpaRepository;
        this.simulationJpaRepository = simulationJpaRepository;
    }

    @Override
    public List<Installment> saveAll(List<Installment> installments) {
        if (installments.isEmpty()) {
            return List.of();
        }

        Long simulationId = installments.get(0).getSimulationId();
        installmentJpaRepository.deleteBySimulationId(simulationId);

        List<InstallmentEntity> entities = installments.stream()
                .map(this::toEntity)
                .toList();

        return installmentJpaRepository.saveAll(entities).stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public List<Installment> findBySimulationId(Long simulationId) {
        return installmentJpaRepository.findBySimulationIdOrderByInstallmentNumber(simulationId)
                .stream()
                .map(this::toDomain)
                .toList();
    }

    private InstallmentEntity toEntity(Installment installment) {
        InstallmentEntity entity = new InstallmentEntity();
        entity.setSimulation(simulationJpaRepository.getReferenceById(installment.getSimulationId()));
        entity.setInstallmentNumber(installment.getInstallmentNumber());
        entity.setCapitalPayment(installment.getCapitalPayment());
        entity.setInterestPayment(installment.getInterestPayment());
        entity.setInstallmentValue(installment.getInstallmentValue());
        entity.setRemainingBalance(installment.getRemainingBalance());
        return entity;
    }

    private Installment toDomain(InstallmentEntity entity) {
        return new Installment(
                entity.getId(),
                entity.getSimulation().getId(),
                entity.getInstallmentNumber(),
                entity.getCapitalPayment(),
                entity.getInterestPayment(),
                entity.getInstallmentValue(),
                entity.getRemainingBalance());
    }
}