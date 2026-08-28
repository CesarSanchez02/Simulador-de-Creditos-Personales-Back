package com.simulador.credito.installment.infrastructure.web;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.simulador.credito.installment.application.dto.InstallmentResponse;
import com.simulador.credito.installment.application.service.InstallmentService;
import com.simulador.credito.shared.ApiSuccessResponse;

@RestController
@RequestMapping("/installments")
public class InstallmentController {

    private final InstallmentService installmentService;

    public InstallmentController(InstallmentService installmentService) {
        this.installmentService = installmentService;
    }

    @GetMapping("/getInstallments/{simulationId}")
    public ResponseEntity<ApiSuccessResponse<List<InstallmentResponse>>> findBySimulationId(
            @PathVariable Long simulationId) {
        return ResponseEntity.ok(new ApiSuccessResponse<>(
                "Tabla de amortización consultada correctamente",
                installmentService.findBySimulationId(simulationId)));
    }
}