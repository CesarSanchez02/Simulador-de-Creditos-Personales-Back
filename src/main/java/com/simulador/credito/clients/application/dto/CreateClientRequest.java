package com.simulador.credito.clients.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateClientRequest {

    @NotBlank(message = "El nombre completo es obligatorio")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    private String fullName;

    @NotNull(message = "El documento es obligatorio")
    @Positive(message = "El documento debe ser positivo")
    private Long documentNumber;

    @NotBlank(message = "El correo es obligatorio")
    @Size(max = 254, message = "El correo no puede superar los 254 caracteres")
    @Email(message = "El correo no tiene un formato válido")
    private String email;

    @NotNull(message = "El teléfono es obligatorio")
    @Positive(message = "El teléfono debe ser positivo")
    private Long phone;
}