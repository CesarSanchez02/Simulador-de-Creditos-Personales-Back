package com.simulador.credito.simulation.application.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SimulationHistoryResponse {

    private Long id;
    private Long clientId;
    private String clientName;
    private LocalDateTime createdAt;
    private BigDecimal requestedAmount;
    private Integer termInMonths;
    private BigDecimal annualInterestRate;
    private BigDecimal monthlyInterestRate;
    private BigDecimal monthlyPayment;
    private BigDecimal totalPayment;
    private BigDecimal totalInterest;
}