package com.rumesh.simpletransitpay.service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.rumesh.simpletransitpay.exception.CsvFileReadException;
import com.rumesh.simpletransitpay.model.EntryRecord;
import com.rumesh.simpletransitpay.types.FileType;
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
public class CsvFileReaderService implements FileReaderService<EntryRecord> {

    /**
     * Method is mainly reads the data from the specified file and returns as list of EntryRecords
     *
     * @param file file path that input data resides
     * @@return List<EntryRecord> returns line count that written in to the file as a integer
     * @author Rumesh
     */
    @Override
    public List<EntryRecord> read(String file) {
        log.info("Csv input file name: {}", file);
        List<EntryRecord> recordList = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new InputStreamReader(new ClassPathResource(file).getInputStream()))) {
            reader.readNext();
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null)  //returns a boolean value
            {
                recordList.add(EntryRecord.fromTokenArray(nextLine));
            }
            return recordList;
        } catch (IOException | CsvValidationException e) {
            log.error("Exception occur while reading csv file", e);
            throw new CsvFileReadException(e.getMessage());
        }
    }

    /**
     * This method can be used to determine whether
     * this class should be use or not to read data based on the file type
     *
     * @param fileType Type of the file that data should be read.
     * @return boolean returns true if file type is CSV else returns false.
     * @author Rumesh
     */
    @Override
    public boolean isApplicable(FileType fileType) {
        return FileType.CSV == fileType;
    }

}
