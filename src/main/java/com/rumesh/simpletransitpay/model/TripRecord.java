package com.rumesh.simpletransitpay.model;

import com.rumesh.simpletransitpay.types.TripStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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

}
