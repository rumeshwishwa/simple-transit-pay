package com.rumesh.simpletransitpay.types;

import java.util.Arrays;

public enum TapType {

    ON,OFF;

    public static TapType getType(String type) {
        return Arrays.stream(values())
                .filter(value -> value.name().equalsIgnoreCase(type))
                .findAny().orElseThrow(RuntimeException::new);
    }
}
