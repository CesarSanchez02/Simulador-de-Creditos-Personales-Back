package com.simulador.credito.installment.domain.repository;

import java.util.List;

import com.simulador.credito.installment.domain.model.Installment;

public interface InstallmentRepository {

    List<Installment> saveAll(List<Installment> installments);

    List<Installment> findBySimulationId(Long simulationId);
}