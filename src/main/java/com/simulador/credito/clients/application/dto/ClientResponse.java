package com.simulador.credito.clients.application.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ClientResponse {

    private Long id;
    private String fullName;
    private Long documentNumber;
    private String email;
    private Long phone;
    private LocalDateTime createdAt;
}