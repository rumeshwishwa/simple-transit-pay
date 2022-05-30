package com.rumesh.simpletransitpay.types;

import com.rumesh.simpletransitpay.exception.NotFoundException;

import java.util.Arrays;

/**
 * TapType is the enum that marked passenger get in and get off respectively ON and OFF
 *
 * @author Rumesh
 * */

public enum TapType {

    ON,OFF;

    /**
     * TapType is the enum that marked passenger get in and get off respectively ON and OFF
     *
     * @author Rumesh
     * @param type type to get as a enum
     * @return TapType returns the matching TapType based on the type input
     * */
    public static TapType getType(String type) {
        return Arrays.stream(values())
                .filter(value -> value.name().equalsIgnoreCase(type))
                .findAny().orElseThrow(() -> new NotFoundException("Input type does not match to any existing TapType."));
    }
}
