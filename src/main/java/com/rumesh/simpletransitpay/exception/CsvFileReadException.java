package com.rumesh.simpletransitpay.exception;

public class CsvFileReadException extends RuntimeException {

    public CsvFileReadException(String errorMsg) {
        super(errorMsg);
    }

}
