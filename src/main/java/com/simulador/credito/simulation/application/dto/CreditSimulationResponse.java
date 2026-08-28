package com.simulador.credito.simulation.application.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreditSimulationResponse {

    private Long id;
    private Long clientId;
    private String clientName;
    private BigDecimal requestedAmount;
    private BigDecimal annualInterestRate;
    private Integer termInMonths;
    private BigDecimal monthlyInterestRate;
    private BigDecimal monthlyPayment;
    private BigDecimal totalPayment;
    private BigDecimal totalInterest;
}