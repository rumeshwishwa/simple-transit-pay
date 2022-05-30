package com.rumesh.simpletransitpay.converter;

@FunctionalInterface
public interface RecordConvertable<T> {

    T convert(String[] tokenArray);

}
