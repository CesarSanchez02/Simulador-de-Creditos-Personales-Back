package com.simulador.credito.simulation.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreditSimulation {

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
    private LocalDateTime createdAt;
}