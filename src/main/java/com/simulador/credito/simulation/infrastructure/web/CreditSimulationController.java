package com.simulador.credito.simulation.infrastructure.web;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.simulador.credito.shared.ApiSuccessResponse;
import com.simulador.credito.simulation.application.dto.CreditSimulationResponse;
import com.simulador.credito.simulation.application.dto.SimulateCreditRequest;
import com.simulador.credito.simulation.application.dto.SimulationHistoryResponse;
import com.simulador.credito.simulation.application.service.CreditSimulationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/simulations")
public class CreditSimulationController {

    private final CreditSimulationService creditSimulationService;

    public CreditSimulationController(CreditSimulationService creditSimulationService) {
        this.creditSimulationService = creditSimulationService;
    }

    @PostMapping("/generate")
    public ResponseEntity<ApiSuccessResponse<CreditSimulationResponse>> simulate(
            @Valid @RequestBody SimulateCreditRequest request) {
        return ResponseEntity.ok(new ApiSuccessResponse<>(
                "Crédito simulado correctamente", creditSimulationService.simulate(request)));
    }

    @GetMapping("/history")
    public ResponseEntity<ApiSuccessResponse<List<SimulationHistoryResponse>>> findHistory() {
        return ResponseEntity.ok(new ApiSuccessResponse<>(
                "Historial de simulaciones consultado correctamente",
                creditSimulationService.findHistory()));
    }
}