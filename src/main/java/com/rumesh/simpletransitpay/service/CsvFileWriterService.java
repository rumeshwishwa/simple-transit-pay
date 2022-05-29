package com.rumesh.simpletransitpay.service;

import com.opencsv.CSVWriter;
import com.rumesh.simpletransitpay.model.TripRecord;
import com.rumesh.simpletransitpay.types.FileType;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CsvFileWriterService implements FileWriterService<TripRecord> {

    @Override
    public void write(List<TripRecord> tripRecords, String file) {
        try (CSVWriter writer = new CSVWriter(new OutputStreamWriter(new FileOutputStream(file)))) {
            final List<String[]> collect = tripRecords.stream()
                    .map(TripRecord::toStringArray)
                    .collect(Collectors.toList());
            collect.add(0, new String[]{"Started", "Finished", "DurationSecs", "FromStopId", "ToStopId", "ChargeAmount",
                    "CompanyId", "BusID", "PAN", "Status"});
            collect.forEach(writer::writeNext);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isApplicable(FileType fileType) {
        return FileType.CSV == fileType;
    }
}
