package com.rumesh.simpletransitpay.model;

import com.rumesh.simpletransitpay.config.DateTimeFormats;
import com.rumesh.simpletransitpay.types.TripStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Model class for Trip Record (Calculated details about the transit).
 *
 * @author Rumesh
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TripRecord {

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private long duration;
    private String fromStopId;
    private String toStopId;
    private double chargeAmount;
    private String companyId;
    private String busId;
    private long pan;
    private TripStatus status;

    /**
     * Returns the string array based on the current properties of TripRecord
     * values in the array ordered based on below format order
     * Started, Finished, DurationSecs, FromStopId, ToStopId, ChargeAmount, CompanyId, BusID, PAN,Status
     *
     * @return String[]
     */
    public String[] toStringArray() {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DateTimeFormats.DD_MM_YYYY_HH_MM_SS);
        return new String[]{
                startTime != null ? startTime.format(formatter) : "",
                endTime != null ? endTime.format(formatter) : "",
                String.valueOf(duration),
                fromStopId,
                toStopId,
                String.valueOf(chargeAmount),
                companyId,
                busId,
                String.valueOf(pan),
                status.name()
        };
    }


}
