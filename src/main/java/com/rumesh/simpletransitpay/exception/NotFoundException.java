package com.rumesh.simpletransitpay.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String errorMsg) {
        super(errorMsg);
    }
}
