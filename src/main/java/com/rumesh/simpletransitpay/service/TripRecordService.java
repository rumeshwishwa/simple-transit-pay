package com.rumesh.simpletransitpay.service;

import com.rumesh.simpletransitpay.config.ConfigProperties;
import com.rumesh.simpletransitpay.model.EntryRecord;
import com.rumesh.simpletransitpay.model.TripRecord;
import com.rumesh.simpletransitpay.types.TripStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Main execution point of the Trip cost calculation
 * Handle trip cost calculation, trip status decision etc.
 *
 * @author Rumesh
 */

@Slf4j
@Service
public class TripRecordService {

    private final UserCacheService userCacheService;
    private final PriceListCacheService priceListCacheService;
    private final CsvFileReaderService csvFileReaderService;
    private final CsvFileWriterService csvFileWriterService;
    private final ConfigProperties configProperties;

    public TripRecordService(final UserCacheService userCacheService,
                             final PriceListCacheService priceListCacheService,
                             final CsvFileReaderService csvFileReaderService,
                             final CsvFileWriterService csvFileWriterService,
                             final ConfigProperties configProperties) {
        this.userCacheService = userCacheService;
        this.priceListCacheService = priceListCacheService;
        this.csvFileReaderService = csvFileReaderService;
        this.csvFileWriterService = csvFileWriterService;
        this.configProperties = configProperties;
    }

    /**
     * execution of trip cost calculation process
     */
    public void execute() {
        log.info("Trip cost calculation process started..");
        final List<EntryRecord> records = csvFileReaderService.read(configProperties.getReadFileName());
        log.info("{} entry records found in the input file",records.size());
        List<TripRecord> tripRecords = records.stream()
                .filter(this::isStartRecordAvailable)
                .map(this::getCancelledOrCompletedTripRecord)
                .collect(Collectors.toList());
        log.info("Cancelled or Completed Trip Record count: {}", tripRecords.size());
        userCacheService.values()
                .stream()
                .map(entryRecord -> this.getCalculatedTripRecord(entryRecord, null, TripStatus.INCOMPLETE))
                .forEach(tripRecords::add);
        log.info("Total trip record count: {}",tripRecords.size());
        csvFileWriterService.write(tripRecords, configProperties.getWriteFileName());
    }

    /**
     * Returns TripRecord based on the starting point details and end point details
     *
     * @param startRecord Starting point details of the passenger trip
     * @param endRecord   End point details of the passenger trip
     * @param status      Passenger trip status
     * @return TripRecord
     */
    public TripRecord getCalculatedTripRecord(@NonNull EntryRecord startRecord,
                                              EntryRecord endRecord,
                                              TripStatus status) {
        TripRecord.TripRecordBuilder recordBuilder = TripRecord.builder()
                .startTime(startRecord.getRecordedTime())
                .endTime(endRecord != null ? endRecord.getRecordedTime() : null)
                .fromStopId(startRecord.getStopId())
                .status(status)
                .companyId(startRecord.getCompanyId())
                .busId(startRecord.getBusID())
                .pan(startRecord.getPan());
        return Optional.ofNullable(endRecord)
                .map(eRecord -> recordBuilder
                        .endTime(eRecord.getRecordedTime())
                        .duration(Duration.between(startRecord.getRecordedTime(), eRecord.getRecordedTime()).getSeconds())
                        .toStopId(eRecord.getStopId())
                        .chargeAmount(priceListCacheService.getPrice(startRecord.getStopId(), eRecord.getStopId())))
                .orElse(recordBuilder)
                .build();
    }

    /**
     * decide whether input record has a previous starting point details in the user record cache or not
     *
     * @param entryRecord EntryRecord details from passenger trip
     * @return true if input entry record has corresponding previous starting point detail else false
     */
    public boolean isStartRecordAvailable(EntryRecord entryRecord) {
        final Optional<EntryRecord> optionalEntryRecord = Optional.ofNullable(userCacheService.get(entryRecord.getPan()));
        if (!optionalEntryRecord.isPresent()) {
            userCacheService.add(entryRecord.getPan(), entryRecord);
        }
        return optionalEntryRecord.isPresent();
    }

    /**
     * Returns Cancelled or Completed TripRecord based on the EntryRecord and User cache
     *
     * @param entryRecord EntryRecord details from passenger trip
     * @return TripRecord
     */
    public TripRecord getCancelledOrCompletedTripRecord(EntryRecord entryRecord) {
        final EntryRecord startRecord = userCacheService.get(entryRecord.getPan());
        userCacheService.remove(startRecord.getPan());
        if (entryRecord.getStopId().equals(startRecord.getStopId())) {
            return this.getCalculatedTripRecord(startRecord, entryRecord, TripStatus.CANCELLED);
        }
        return this.getCalculatedTripRecord(startRecord, entryRecord, TripStatus.COMPLETED);
    }

}
