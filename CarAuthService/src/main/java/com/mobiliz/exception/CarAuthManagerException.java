package com.mobiliz.exception;

import lombok.Getter;

@Getter
public class CarAuthManagerException extends RuntimeException{

    private final ErrorType errorType;

    public CarAuthManagerException(ErrorType errorType, String customMessage) {
        super(customMessage);
        this.errorType = errorType;
    }

    public CarAuthManagerException(ErrorType errorType){
        this.errorType = errorType;
    }
}
