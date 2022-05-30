package com.rumesh.simpletransitpay.exception;

public class CsvFileWriteException extends RuntimeException {

    public CsvFileWriteException(String errorMsg) {
        super(errorMsg);
    }
}
