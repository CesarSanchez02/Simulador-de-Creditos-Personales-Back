package com.simulador.credito.clients.application.exception;

public class ClientNotFoundException extends RuntimeException {

    public ClientNotFoundException(Long id) {
        super("Cliente no encontrado con id: " + id);
    }
}