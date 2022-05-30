package com.rumesh.simpletransitpay.converter;

@FunctionalInterface
public interface StringConvertable<T> {

    String[] convert(T record);

}
