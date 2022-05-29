package com.rumesh.simpletransitpay.util;

import com.rumesh.simpletransitpay.config.DateTimeFormats;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {

    public static LocalDateTime getDateTime(String date) {
        return LocalDateTime.parse(date, DateTimeFormatter.ofPattern(DateTimeFormats.DD_MM_YYYY_HH_MM_SS));
    }
}
