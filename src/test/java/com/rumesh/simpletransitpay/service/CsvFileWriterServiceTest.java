package com.rumesh.simpletransitpay.service;

import com.rumesh.simpletransitpay.model.TripRecord;
import com.rumesh.simpletransitpay.types.FileType;
import com.rumesh.simpletransitpay.types.TripStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CsvFileWriterServiceTest {

    private CsvFileWriterService csvFileWriterService;
    private List<TripRecord> tripRecords;

    @BeforeEach
    void setUp() {
        csvFileWriterService = new CsvFileWriterService();
        tripRecords = new ArrayList<>();
        tripRecords.add(TripRecord.builder()
                .startTime(LocalDateTime.now().minusMinutes(5))
                .endTime(LocalDateTime.now())
                .fromStopId("StopId1")
                .toStopId("StopId2")
                .companyId("Company1")
                .busId("Bus1")
                .chargeAmount(45)
                .status(TripStatus.COMPLETED)
                .pan(2335)
                .duration(25)
                .build());
    }

    @Test
    void givenTripRecordsThenSuccessAndReturnLineCount() {
        int lineCount = csvFileWriterService.write(tripRecords, "sample-out.csv");
        assertEquals(2, lineCount);
    }

    @Test
    void givenCsvFileTypeThenSuccessAndReturnTrue() {
        csvFileWriterService.isApplicable(FileType.CSV);
    }

    @Test
    void givenOtherFileTypePassedThenSuccessAndReturnFalse() {
        csvFileWriterService.isApplicable(FileType.EXCEL);
    }
}
