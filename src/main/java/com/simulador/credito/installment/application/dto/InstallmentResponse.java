package com.simulador.credito.installment.application.dto;

import java.math.BigDecimal;

import com.simulador.credito.installment.domain.model.Installment;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InstallmentResponse {

    private Long id;
    private Long simulationId;
    private Integer installmentNumber;
    private BigDecimal capitalPayment;
    private BigDecimal interestPayment;
    private BigDecimal installmentValue;
    private BigDecimal remainingBalance;

    public static InstallmentResponse from(Installment installment) {
        return new InstallmentResponse(
                installment.getId(), installment.getSimulationId(), installment.getInstallmentNumber(),
                installment.getCapitalPayment(), installment.getInterestPayment(),
                installment.getInstallmentValue(), installment.getRemainingBalance());
    }
}