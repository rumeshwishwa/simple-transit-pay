package com.rumesh.simpletransitpay.service;

import com.rumesh.simpletransitpay.exception.CsvFileReadException;
import com.rumesh.simpletransitpay.types.FileType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class CsvFileReaderServiceTest {

    private CsvFileReaderService csvFileReaderService;

    @BeforeEach
    void setUp() {
        csvFileReaderService = new CsvFileReaderService();
    }

    @Test
    void givenIncorrectFileNameThenFailedAndThrowCsvFileReadException() {
        CsvFileReadException exception = assertThrows(CsvFileReadException.class,
                () -> csvFileReaderService.read("not-found-file.csv"));
    }

    @Test
    void givenProperFileNameThenSuccessAndReturnEntryRecords() {
        csvFileReaderService.read("testdata.csv");
    }

    @Test
    void givenCsvFileTypeThenSuccessAndReturnTrue() {
        csvFileReaderService.isApplicable(FileType.CSV);
    }

    @Test
    void givenOtherFileTypeThenSuccessAndReturnFalse() {
        csvFileReaderService.isApplicable(FileType.EXCEL);
    }
}
