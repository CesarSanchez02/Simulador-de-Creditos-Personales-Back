package com.simulador.credito.clients.infrastructure.web;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.simulador.credito.clients.application.dto.ClientResponse;
import com.simulador.credito.clients.application.dto.CreateClientRequest;
import com.simulador.credito.clients.application.service.ClientService;
import com.simulador.credito.shared.ApiSuccessResponse;

import org.springframework.http.HttpStatus;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/create")
    public ResponseEntity<ApiSuccessResponse<ClientResponse>> create(
            @Valid @RequestBody CreateClientRequest request) {
        ClientResponse client = clientService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiSuccessResponse<>("Cliente creado correctamente", client));
    }

    @GetMapping
    public ResponseEntity<ApiSuccessResponse<List<ClientResponse>>> findAll() {
        return ResponseEntity.ok(new ApiSuccessResponse<>(
                "Clientes consultados correctamente", clientService.findAll()));
    }

    @GetMapping("getById/{id}")
    public ResponseEntity<ApiSuccessResponse<ClientResponse>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiSuccessResponse<>(
                "Cliente consultado correctamente", clientService.findById(id)));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiSuccessResponse<ClientResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody CreateClientRequest request) {
        return ResponseEntity.ok(new ApiSuccessResponse<>(
                "Cliente actualizado correctamente", clientService.update(id, request)));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiSuccessResponse<Void>> delete(@PathVariable Long id) {
        clientService.delete(id);
        return ResponseEntity.ok(new ApiSuccessResponse<>(
                "Cliente eliminado correctamente", null));
    }
}