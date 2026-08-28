package com.simulador.credito.simulation.infrastructure.persistence.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.simulador.credito.clients.infrastructure.persistence.entity.ClientEntity;
import com.simulador.credito.installment.infrastructure.persistence.entity.InstallmentEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "simulations")
@Getter
@Setter
@NoArgsConstructor
public class SimulationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "clientid", nullable = false)
    private ClientEntity client;

    @Column(name = "requestedamount", nullable = false, precision = 17, scale = 2)
    private BigDecimal requestedAmount;

    @Column(name = "annualinterestrate", nullable = false, precision = 7, scale = 2)
    private BigDecimal annualInterestRate;

    @Column(name = "terminmonths", nullable = false)
    private Integer termInMonths;

    @Column(name = "monthlyinterestrate", nullable = false, precision = 4, scale = 4)
    private BigDecimal monthlyInterestRate;

    @Column(name = "monthlypayment", nullable = false, precision = 17, scale = 2)
    private BigDecimal monthlyPayment;

    @Column(name = "totalpayment", nullable = false, precision = 17, scale = 2)
    private BigDecimal totalPayment;

    @Column(name = "totalinterest", nullable = false, precision = 17, scale = 2)
    private BigDecimal totalInterest;

    @Column(name = "createdat", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "simulation")
    private List<InstallmentEntity> installments = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }
}