package com.rumesh.simpletransitpay.service;

import com.rumesh.simpletransitpay.converter.EntryRecordConverter;
import com.rumesh.simpletransitpay.exception.CsvFileReadException;
import com.rumesh.simpletransitpay.model.EntryRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class CsvFileReaderServiceTest {

    @Mock
    private EntryRecordConverter entryRecordConverter;

    private CsvFileReaderService<EntryRecord> csvFileReaderService;

    @BeforeEach
    void setUp() {
        csvFileReaderService = new CsvFileReaderService<>();
    }

    @Test
    void givenIncorrectFileNameThenFailedAndThrowCsvFileReadException() {
        CsvFileReadException exception = assertThrows(CsvFileReadException.class,
                () -> csvFileReaderService.read("not-found-file.csv", entryRecordConverter));
    }

    @Test
    void givenProperFileNameThenSuccessAndReturnEntryRecords() {
        csvFileReaderService.read("testdata.csv", entryRecordConverter);
    }
}
