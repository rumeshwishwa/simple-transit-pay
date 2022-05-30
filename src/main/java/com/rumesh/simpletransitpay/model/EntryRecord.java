package com.rumesh.simpletransitpay.model;

import com.rumesh.simpletransitpay.types.TapType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Model mapping class for entry records that matched to input data file
 *
 * @author Rumesh
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EntryRecord {
    private int id;
    private LocalDateTime recordedTime;
    private TapType tapType;
    private String stopId;
    private String companyId;
    private String busID;
    private long pan;
}
