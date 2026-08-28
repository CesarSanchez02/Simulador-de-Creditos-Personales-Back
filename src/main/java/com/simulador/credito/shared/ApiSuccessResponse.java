package com.simulador.credito.shared;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiSuccessResponse<T> {

    private String message;
    private T data;
}