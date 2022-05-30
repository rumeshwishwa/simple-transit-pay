package com.rumesh.simpletransitpay.service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.rumesh.simpletransitpay.converter.RecordConvertable;
import com.rumesh.simpletransitpay.exception.CsvFileReadException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Class can be used to read data from a csv file and will be returns the data as EntryRecord list
 *
 * @author Rumesh
 */

@Slf4j
@Service
public class CsvFileReaderService<T> {

    /**
     * Method is mainly reads the data from the specified file and returns as list of EntryRecords
     *
     * @param file file path that input data resides
     * @@return List<EntryRecord> returns line count that written in to the file as a integer
     * @author Rumesh
     */
    public List<T> read(String file, RecordConvertable<T> recordConvertable) {
        log.info("Csv input file name: {}", file);
        List<T> recordList = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new InputStreamReader(new ClassPathResource(file).getInputStream()))) {
            reader.readNext();
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null)  //returns a boolean value
            {
                recordList.add(recordConvertable.convert(nextLine));
            }
            return recordList;
        } catch (IOException | CsvValidationException e) {
            log.error("Exception occur while reading csv file", e);
            throw new CsvFileReadException(e.getMessage());
        }
    }

}
