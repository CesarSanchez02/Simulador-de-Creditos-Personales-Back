package com.simulador.credito.simulation.application.service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simulador.credito.clients.application.exception.ClientNotFoundException;
import com.simulador.credito.clients.domain.model.Client;
import com.simulador.credito.clients.domain.repository.ClientRepository;
import com.simulador.credito.installment.application.service.InstallmentService;
import com.simulador.credito.simulation.application.dto.CreditSimulationResponse;
import com.simulador.credito.simulation.application.dto.SimulateCreditRequest;
import com.simulador.credito.simulation.application.dto.SimulationHistoryResponse;
import com.simulador.credito.simulation.domain.model.CreditSimulation;
import com.simulador.credito.simulation.domain.repository.SimulationRepository;

@Service
public class CreditSimulationService {

    private static final MathContext CALCULATION_CONTEXT = new MathContext(20, RoundingMode.HALF_UP);
    private static final int MONEY_SCALE = 2;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private SimulationRepository simulationRepository;

    @Autowired
    private InstallmentService installmentService;

    public CreditSimulationResponse simulate(SimulateCreditRequest request) {
        Client client = clientRepository.findById(request.getClientId())
                .orElseThrow(() -> new ClientNotFoundException(request.getClientId()));

        BigDecimal monthlyInterest = calculateMonthlyInterest(request.getAnnualInterestRate());

        BigDecimal monthlyPayment = calculateMonthlyPayment(
                request.getRequestedAmount(), monthlyInterest, request.getTermInMonths());

        BigDecimal totalPayment = monthlyPayment.multiply(
                BigDecimal.valueOf(request.getTermInMonths()), CALCULATION_CONTEXT)
                .setScale(MONEY_SCALE, RoundingMode.HALF_UP);

        BigDecimal totalInterest = totalPayment.subtract(request.getRequestedAmount())
                .setScale(MONEY_SCALE, RoundingMode.HALF_UP);

        CreditSimulation simulation = new CreditSimulation(
                null,
                request.getClientId(),
                client.getFullName(),
                request.getRequestedAmount(),
                request.getAnnualInterestRate(),
                request.getTermInMonths(),
                monthlyInterest,
                monthlyPayment,
                totalPayment,
                totalInterest,
                null);

        CreditSimulation savedSimulation = simulationRepository.save(simulation);
        installmentService.generate(savedSimulation.getId());

        return new CreditSimulationResponse(
                savedSimulation.getId(),
                savedSimulation.getClientId(),
                savedSimulation.getClientName(),
                savedSimulation.getRequestedAmount(),
                savedSimulation.getAnnualInterestRate(),
                savedSimulation.getTermInMonths(),
                savedSimulation.getMonthlyInterestRate()
                        .multiply(BigDecimal.valueOf(100))
                        .setScale(2, RoundingMode.HALF_UP),
                savedSimulation.getMonthlyPayment(),
                savedSimulation.getTotalPayment(),
                savedSimulation.getTotalInterest());
    }

        public List<SimulationHistoryResponse> findHistory() {
                return simulationRepository.findAll().stream()
                .map(this::toHistoryResponse)
                .toList();
    }

    private SimulationHistoryResponse toHistoryResponse(CreditSimulation simulation) {
        return new SimulationHistoryResponse(
                simulation.getId(),
                simulation.getClientId(),
                simulation.getClientName(),
                simulation.getCreatedAt(),
                simulation.getRequestedAmount(),
                simulation.getTermInMonths(),
                simulation.getAnnualInterestRate(),
                simulation.getMonthlyInterestRate()
                        .multiply(BigDecimal.valueOf(100))
                        .setScale(2, RoundingMode.HALF_UP),
                simulation.getMonthlyPayment(),
                simulation.getTotalPayment(),
                simulation.getTotalInterest());
    }

    private BigDecimal calculateMonthlyInterest(BigDecimal annualRate) {
        double annualRateValue = annualRate.divide(BigDecimal.valueOf(100), CALCULATION_CONTEXT).doubleValue();

        return BigDecimal.valueOf(Math.pow(1 + annualRateValue, 1.0 / 12) - 1)
                .setScale(10, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateMonthlyPayment(BigDecimal amount, BigDecimal monthlyInterest, int months) {

        if (monthlyInterest.signum() == 0) {
            return amount.divide(BigDecimal.valueOf(months), MONEY_SCALE, RoundingMode.HALF_UP);
        }

        double amountRequest = amount.doubleValue();

        double i = monthlyInterest.doubleValue();

        double power = Math.pow(1 + i, months);

        double numerator = i * power;

        double denominator = power - 1;

        double payment = amountRequest * (numerator / denominator);

        return BigDecimal.valueOf(payment).setScale(MONEY_SCALE, RoundingMode.HALF_UP);
    }
}