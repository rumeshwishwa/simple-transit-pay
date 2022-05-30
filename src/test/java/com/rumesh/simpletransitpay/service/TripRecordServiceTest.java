package com.rumesh.simpletransitpay.service;

import com.rumesh.simpletransitpay.config.ConfigProperties;
import com.rumesh.simpletransitpay.exception.CsvFileReadException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class TripRecordServiceTest {

    private TripRecordService tripRecordService;

    @Mock
    private UserCacheService userCacheService;

    @Mock
    private PriceListCacheService priceListCacheService;

    @Mock
    private CsvFileReaderService csvFileReaderService;

    @Mock
    private CsvFileWriterService csvFileWriterService;

    @Mock
    private ConfigProperties configProperties;

    @BeforeEach
    void setUp() {
        tripRecordService = new TripRecordService(userCacheService, priceListCacheService,
                csvFileReaderService, csvFileWriterService, configProperties);
    }

    @Test
    void givenIncorrectFileNameThenFailedAndThrowCsvFileReadException() {
        CsvFileReadException exception = assertThrows(CsvFileReadException.class,
                () -> csvFileReaderService.read("not-found-file.csv"));
    }
}
