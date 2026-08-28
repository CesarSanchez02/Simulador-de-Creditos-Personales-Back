package com.simulador.credito.clients.application.exception;

public class DuplicateClientException extends RuntimeException {

    public DuplicateClientException() {
        super("Ya existe un cliente con ese documento");
    }
}