package com.rumesh.simpletransitpay.util;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class DateTimeUtilTest {

    @Test
    void givenCorrectFormattedDateThenSuccessAndReturnDateTime() {
        final LocalDateTime dateTime = DateTimeUtil.getDateTime("22-01-2018 13:00:00");
        assertEquals(13, dateTime.getHour());
    }

    @Test
    void givenIncorrectFormattedDateThenFailedAndThrowException() {
        Exception exception = assertThrows(Exception.class,
                () -> DateTimeUtil.getDateTime("01-22-2018 13:00:00"));
    }

}
