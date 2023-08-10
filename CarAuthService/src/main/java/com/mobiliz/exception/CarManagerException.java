package com.mobiliz.exception;

import lombok.Getter;

@Getter
public class CarManagerException extends RuntimeException{

    private final ErrorType errorType;

    public CarManagerException(ErrorType errorType, String customMessage) {
        super(customMessage);
        this.errorType = errorType;
    }

    public CarManagerException(ErrorType errorType){
        this.errorType = errorType;
    }
}
