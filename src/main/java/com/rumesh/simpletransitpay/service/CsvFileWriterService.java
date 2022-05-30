package com.rumesh.simpletransitpay.service;

import com.opencsv.CSVWriter;
import com.rumesh.simpletransitpay.exception.CsvFileWriteException;
import com.rumesh.simpletransitpay.model.TripRecord;
import com.rumesh.simpletransitpay.types.FileType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class can be used to write list of TripRecord data in to a csv file
 *
 * @author Rumesh
 */

@Slf4j
@Service
public class CsvFileWriterService implements FileWriterService<TripRecord> {


    /**
     * Class can be used to write TripRecord list in to csv file
     *
     * @param tripRecords List of Trip Records that need to be written in to the csv file
     * @param file        file path that trip records should be written
     * @@return int returns line count that written in to the file as a integer
     * @author Rumesh
     */
    @Override
    public int write(List<TripRecord> tripRecords, String file) {
        log.info("Csv output file name: {}",file);
        log.info("Final Trip Record count : {}",tripRecords.size());
        try (CSVWriter writer = new CSVWriter(new OutputStreamWriter(new FileOutputStream(file)))) {
            final List<String[]> tripRecordStringArrays = tripRecords.stream()
                    .map(TripRecord::toStringArray)
                    .collect(Collectors.toList());
            tripRecordStringArrays.add(0, new String[]{"Started", "Finished", "DurationSecs", "FromStopId", "ToStopId", "ChargeAmount",
                    "CompanyId", "BusID", "PAN", "Status"});
            tripRecordStringArrays.forEach(writer::writeNext);
            return tripRecordStringArrays.size();
        } catch (IOException e) {
            log.error("Exception while writing data in to csv output file");
            throw new CsvFileWriteException(e.getMessage());
        }
    }

    /**
     * This method can be used to determine whether
     * this class should be use or not to write data based on the file type
     *
     * @param fileType Type of the file that data should be written.
     * @return boolean returns true if file type is CSV else returns false.
     * @author Rumesh
     */
    @Override
    public boolean isApplicable(FileType fileType) {
        return FileType.CSV == fileType;
    }
}
