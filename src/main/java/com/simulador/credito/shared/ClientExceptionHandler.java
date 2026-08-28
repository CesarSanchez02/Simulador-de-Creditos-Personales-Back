package com.simulador.credito.shared;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.simulador.credito.clients.application.exception.ClientNotFoundException;
import com.simulador.credito.clients.application.exception.DuplicateClientException;

@RestControllerAdvice
public class ClientExceptionHandler {

    @ExceptionHandler(ClientNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleNotFound(ClientNotFoundException exception) {
        return buildResponse(HttpStatus.NOT_FOUND, exception.getMessage(), null);
    }

    @ExceptionHandler(DuplicateClientException.class)
    public ResponseEntity<ApiErrorResponse> handleDuplicate(DuplicateClientException exception) {
        return buildResponse(HttpStatus.BAD_REQUEST, exception.getMessage(), null);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiErrorResponse> handleDataIntegrityViolation(
            DataIntegrityViolationException exception) {
        return buildResponse(
                HttpStatus.CONFLICT,
                "No se puede eliminar el cliente porque tiene simulaciones asociadas",
                null);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidation(MethodArgumentNotValidException exception) {
        Map<String, String> errors = exception.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(
                        error -> error.getField(),
                        error -> error.getDefaultMessage(),
                        (firstMessage, secondMessage) -> firstMessage));

        return buildResponse(HttpStatus.BAD_REQUEST, "Datos de entrada inválidos", errors);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiErrorResponse> handleTypeMismatch(
            MethodArgumentTypeMismatchException exception) {
        return buildResponse(
                HttpStatus.BAD_REQUEST,
                "El parámetro '" + exception.getName() + "' no tiene un formato válido",
                null);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleUnexpected(Exception exception) {
        return buildResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Ocurrió un error interno en el servidor",
                null);
    }

    private ResponseEntity<ApiErrorResponse> buildResponse(
            HttpStatus status, String message, Map<String, String> errors) {
        ApiErrorResponse response = new ApiErrorResponse(
                LocalDateTime.now(), status.value(), message, errors);
        return ResponseEntity.status(status).body(response);
    }
}