package com.simulador.credito.installment.application.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.simulador.credito.clients.application.exception.ClientNotFoundException;
import com.simulador.credito.installment.application.dto.InstallmentResponse;
import com.simulador.credito.installment.domain.model.Installment;
import com.simulador.credito.installment.domain.repository.InstallmentRepository;
import com.simulador.credito.simulation.domain.model.CreditSimulation;
import com.simulador.credito.simulation.domain.repository.SimulationRepository;

@Service
public class InstallmentService {

    private static final int MONEY_SCALE = 2;

    private final InstallmentRepository installmentRepository;
    private final SimulationRepository simulationRepository;

    public InstallmentService(InstallmentRepository installmentRepository,
            SimulationRepository simulationRepository) {
        this.installmentRepository = installmentRepository;
        this.simulationRepository = simulationRepository;
    }

    @Transactional
    public List<InstallmentResponse> generate(Long simulationId) {

        CreditSimulation simulation = simulationRepository.findById(simulationId)
                .orElseThrow(() -> new ClientNotFoundException(simulationId));

        BigDecimal remainingBalance = simulation.getRequestedAmount();

        BigDecimal monthlyRate = simulation.getMonthlyInterestRate();

        BigDecimal payment = simulation.getMonthlyPayment();

        List<Installment> installments = new java.util.ArrayList<>();

        for (int installmentNumber = 1; installmentNumber <= simulation.getTermInMonths()
                && remainingBalance.signum() > 0; installmentNumber++) {

            BigDecimal interest = remainingBalance.multiply(monthlyRate)
                    .setScale(MONEY_SCALE, RoundingMode.HALF_UP);

            BigDecimal capitalPayment = payment.subtract(interest)
                    .setScale(MONEY_SCALE, RoundingMode.HALF_UP);

            capitalPayment = capitalPayment.min(remainingBalance);

            BigDecimal installmentValue = capitalPayment.add(interest)
                    .setScale(MONEY_SCALE, RoundingMode.HALF_UP);

            BigDecimal balanceAfterPayment = remainingBalance.subtract(capitalPayment)
                    .setScale(MONEY_SCALE, RoundingMode.HALF_UP);

            installments.add(new Installment(
                    null, simulationId, installmentNumber, capitalPayment, interest,
                    installmentValue, balanceAfterPayment));
            remainingBalance = balanceAfterPayment;
        }

        return installmentRepository.saveAll(installments).stream()
                .map(InstallmentResponse::from)
                .toList();
    }

    public List<InstallmentResponse> findBySimulationId(Long simulationId) {
        if (simulationRepository.findById(simulationId).isEmpty()) {
            throw new ClientNotFoundException(simulationId);
        }

        return installmentRepository.findBySimulationId(simulationId).stream()
                .map(InstallmentResponse::from)
                .toList();
    }
}