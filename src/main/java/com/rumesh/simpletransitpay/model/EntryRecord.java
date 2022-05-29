package com.rumesh.simpletransitpay.model;

import com.rumesh.simpletransitpay.types.TapType;
import com.rumesh.simpletransitpay.util.DateTimeUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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


    /**
     * Returns a EntryRecord from input string string[]
     *
     * @param tokenArray array converted input file data line
     * @return EntryRecord that transformed from input file data line
     */
    public static EntryRecord fromTokenArray(String[] tokenArray) {
        final List<String> tokens = Arrays.stream(tokenArray)
                .map(String::trim)
                .collect(Collectors.toList());

        return EntryRecord.builder()
                .id(Integer.parseInt(tokens.get(0)))
                .recordedTime(DateTimeUtil.getDateTime(tokens.get(1)))
                .tapType(TapType.getType(tokens.get(2)))
                .stopId(tokens.get(3))
                .companyId(tokens.get(4))
                .busID(tokens.get(5))
                .pan(Long.parseLong(tokens.get(6)))
                .build();
    }
}
