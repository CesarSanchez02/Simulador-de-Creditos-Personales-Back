package com.simulador.credito.installment.infrastructure.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.simulador.credito.installment.infrastructure.persistence.entity.InstallmentEntity;

public interface InstallmentJpaRepository extends JpaRepository<InstallmentEntity, Long> {

    List<InstallmentEntity> findBySimulationIdOrderByInstallmentNumber(Long simulationId);

    void deleteBySimulationId(Long simulationId);
}