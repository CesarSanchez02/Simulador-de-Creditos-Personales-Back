package com.simulador.credito.clients.domain.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Client {

    private Long id;

    private String fullName;

    private Long documentNumber;

    private String email;

    private Long phone;
    
    private LocalDateTime createdAt;
}
