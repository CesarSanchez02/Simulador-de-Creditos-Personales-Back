package com.simulador.credito.installment.infrastructure.persistence.entity;

import java.math.BigDecimal;

import com.simulador.credito.simulation.infrastructure.persistence.entity.SimulationEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "installments")
@Getter
@Setter
@NoArgsConstructor
public class InstallmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "simulationid", nullable = false)
    private SimulationEntity simulation;

    @Column(name = "installmentnumber", nullable = false)
    private Integer installmentNumber;

    @Column(name = "capitalpayment", nullable = false, precision = 17, scale = 2)
    private BigDecimal capitalPayment;

    @Column(name = "interestpayment", nullable = false, precision = 17, scale = 2)
    private BigDecimal interestPayment;

    @Column(name = "installmentvalue", nullable = false, precision = 17, scale = 2)
    private BigDecimal installmentValue;

    @Column(name = "remainingbalance", nullable = false, precision = 17, scale = 2)
    private BigDecimal remainingBalance;
}