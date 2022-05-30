package com.rumesh.simpletransitpay.util;

import com.rumesh.simpletransitpay.config.DateTimeFormats;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Class used to do the application specific date time operations
 *
 * @author Rumesh
 */

public class DateTimeUtil {

    /**
     * Class used to do the application specific date time operations
     *
     * @author Rumesh
     * @param date String date that need to convert to LocalDateTime
     */
    public static LocalDateTime getDateTime(String date) {
        return LocalDateTime.parse(date, DateTimeFormatter.ofPattern(DateTimeFormats.DD_MM_YYYY_HH_MM_SS));
    }
}
