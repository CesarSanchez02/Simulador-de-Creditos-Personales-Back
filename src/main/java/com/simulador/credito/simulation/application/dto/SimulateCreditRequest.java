package com.simulador.credito.simulation.application.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SimulateCreditRequest {

    @NotNull(message = "El cliente es obligatorio")
    @Positive(message = "El id del cliente debe ser positivo")
    private Long clientId;

    @NotNull(message = "El monto solicitado es obligatorio")
    @Positive(message = "El monto solicitado debe ser positivo")
    @Digits(integer = 15, fraction = 2, message = "El monto solicitado no es válido")
    private BigDecimal requestedAmount;

    @NotNull(message = "La tasa anual es obligatoria")
    @DecimalMin(value = "0.0", message = "La tasa anual no puede ser negativa")
    @DecimalMax(value = "100.0", message = "La tasa anual no puede superar el 100%")
    @Digits(integer = 3, fraction = 4, message = "La tasa anual no es válida")
    private BigDecimal annualInterestRate;

    @NotNull(message = "El plazo es obligatorio")
    @Positive(message = "El plazo debe ser positivo")
    private Integer termInMonths;
}