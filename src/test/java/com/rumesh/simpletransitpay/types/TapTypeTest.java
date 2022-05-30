package com.rumesh.simpletransitpay.types;

import com.rumesh.simpletransitpay.exception.NotFoundException;
import com.rumesh.simpletransitpay.util.DateTimeUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class TapTypeTest {

    @Test
    void givenValidTypeThenSuccessAndReturnTapType() {
        final TapType tapType = TapType.getType("ON");
        assertEquals(TapType.ON, tapType);
    }

    @Test
    void givenIncorrectFormattedDateThenFailedAndThrowException() {
        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> TapType.getType("OF"));
        assertEquals("Input type does not match to any existing TapType.", exception.getMessage());
    }
}
