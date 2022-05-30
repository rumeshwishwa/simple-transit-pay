package com.rumesh.simpletransitpay.converter;

import com.rumesh.simpletransitpay.model.EntryRecord;
import com.rumesh.simpletransitpay.types.TapType;
import com.rumesh.simpletransitpay.util.DateTimeUtil;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class EntryRecordConverter implements RecordConvertable<EntryRecord> {

    /**
     * Returns a EntryRecord from input string string[]
     *
     * @param tokenArray array converted input file data line
     * @return EntryRecord that transformed from input file data line
     */
    @Override
    public EntryRecord convert(String[] tokenArray) {
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
