package com.rumesh.simpletransitpay.service;

import com.rumesh.simpletransitpay.config.ConfigProperties;
import com.rumesh.simpletransitpay.converter.EntryRecordConverter;
import com.rumesh.simpletransitpay.converter.TripRecordStringConverter;
import com.rumesh.simpletransitpay.model.EntryRecord;
import com.rumesh.simpletransitpay.model.TripRecord;
import com.rumesh.simpletransitpay.types.TripStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TripRecordServiceTest {

    private TripRecordService tripRecordService;

    @Mock
    private UserCacheService userCacheService;

    @Mock
    private PriceListCacheService priceListCacheService;

    @Mock
    private MaximumTravelLocationCache maximumTravelLocationCache;

    @Mock
    private CsvFileReaderService csvFileReaderService;

    @Mock
    private CsvFileWriterService csvFileWriterService;

    @Mock
    private EntryRecordConverter entryRecordConverter;

    @Mock
    private TripRecordStringConverter tripRecordStringConverter;

    @Mock
    private ConfigProperties configProperties;

    private EntryRecord entryRecord1;
    private EntryRecord entryRecord2;
    private EntryRecord entryRecord3;

    @BeforeEach
    void setUp() {
        tripRecordService = new TripRecordService(userCacheService, priceListCacheService,
                maximumTravelLocationCache, csvFileReaderService, csvFileWriterService, configProperties, entryRecordConverter,
                tripRecordStringConverter);
        entryRecord1 = EntryRecord.builder()
                .pan(123)
                .stopId("Stop1")
                .busID("BusId1")
                .companyId("CompanyId1")
                .recordedTime(LocalDateTime.now().minusMinutes(5))
                .build();
        entryRecord2 = EntryRecord.builder()
                .pan(123)
                .stopId("Stop2")
                .busID("BusId1")
                .companyId("CompanyId1")
                .recordedTime(LocalDateTime.now())
                .build();
        entryRecord3 = EntryRecord.builder()
                .pan(123)
                .stopId("Stop1")
                .busID("BusId1")
                .companyId("CompanyId1")
                .recordedTime(LocalDateTime.now())
                .build();
    }

    @Test
    public void givenThatExistingUserOFFRecordThenReturnProperCompletedTripRecord() {
        when(userCacheService.get(entryRecord1.getPan(),entryRecord1.getBusID())).thenReturn(entryRecord1);
        when(priceListCacheService.getPrice("Stop1","Stop2")).thenReturn(3.25);
        final TripRecord tripRecord = tripRecordService.getCancelledOrCompletedTripRecord(entryRecord2);
        assertEquals(entryRecord1.getRecordedTime(), tripRecord.getStartTime());
        assertEquals(entryRecord2.getRecordedTime(), tripRecord.getEndTime());
        assertEquals(Duration.between(entryRecord1.getRecordedTime(), entryRecord2.getRecordedTime()).getSeconds(),
                tripRecord.getDuration());
        assertEquals(entryRecord1.getStopId(), tripRecord.getFromStopId());
        assertEquals(entryRecord2.getStopId(), tripRecord.getToStopId());
        assertEquals(3.25, tripRecord.getChargeAmount());
        assertEquals("CompanyId1", tripRecord.getCompanyId());
        assertEquals("BusId1", tripRecord.getBusId());
        assertEquals(123, tripRecord.getPan());
        assertEquals(TripStatus.COMPLETED, tripRecord.getStatus());
        verify(userCacheService, times(1)).get(entryRecord1.getPan(), entryRecord1.getBusID());
        verify(priceListCacheService, times(1)).getPrice("Stop1","Stop2");
    }

    @Test
    public void givenThatExistingUserOnRecordThenReturnTrue() {
        when(userCacheService.get(entryRecord1.getPan(),entryRecord1.getBusID())).thenReturn(entryRecord1);
        final boolean startRecordAvailable = tripRecordService.isStartRecordAvailable(entryRecord2);
        assertTrue(startRecordAvailable);
    }

    @Test
    public void givenThatNonExistingUserOnRecordThenReturnFalse() {
        final boolean startRecordAvailable = tripRecordService.isStartRecordAvailable(entryRecord1);
        assertFalse(startRecordAvailable);
        verify(userCacheService, times(1)).add(123L, entryRecord1.getBusID(), entryRecord1);
    }

    @Test
    public void givenThatExistingUserOFFRecordThenReturnCompletedTripRecord() {
        when(userCacheService.get(entryRecord1.getPan(),entryRecord1.getBusID())).thenReturn(entryRecord1);
        final TripRecord completedTripRecord = tripRecordService.getCancelledOrCompletedTripRecord(entryRecord2);
        assertEquals(TripStatus.COMPLETED, completedTripRecord.getStatus());
        verify(userCacheService, times(1)).remove(entryRecord1.getPan(), entryRecord1.getBusID());
    }

    @Test
    public void givenThatSameStopsExistingUserOFFRecordThenReturnCancelledTripRecord() {
        when(userCacheService.get(entryRecord1.getPan(),entryRecord1.getBusID())).thenReturn(entryRecord1);
        final TripRecord completedTripRecord = tripRecordService.getCancelledOrCompletedTripRecord(entryRecord3);
        assertEquals(TripStatus.CANCELLED, completedTripRecord.getStatus());
        verify(userCacheService, times(1)).remove(entryRecord1.getPan(), entryRecord1.getBusID());
    }

    @Test
    public void givenThatNonExistingUserOFFRecordThenThrowNPE() {
        when(userCacheService.get(123L, "BusNon1")).thenReturn(null);
        NullPointerException exception = assertThrows(NullPointerException.class,
                () -> tripRecordService.getCancelledOrCompletedTripRecord(entryRecord3));
    }

}
