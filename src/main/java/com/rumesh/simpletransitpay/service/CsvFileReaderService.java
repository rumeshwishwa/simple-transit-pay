package com.rumesh.simpletransitpay.service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.rumesh.simpletransitpay.model.EntryRecord;
import com.rumesh.simpletransitpay.types.FileType;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class CsvFileReaderService implements FileReaderService<EntryRecord> {

    @Override
    public List<EntryRecord> read(String file) {
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
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Override
    public boolean isApplicable(FileType fileType) {
        return FileType.CSV == fileType;
    }

}
