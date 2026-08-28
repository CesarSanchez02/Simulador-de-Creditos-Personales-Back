package com.simulador.credito.installment.domain.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Installment {

    private Long id;
    private Long simulationId;
    private Integer installmentNumber;
    private BigDecimal capitalPayment;
    private BigDecimal interestPayment;
    private BigDecimal installmentValue;
    private BigDecimal remainingBalance;
}