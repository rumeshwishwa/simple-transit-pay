package com.rumesh.simpletransitpay.service;

import com.rumesh.simpletransitpay.config.ConfigProperties;
import com.rumesh.simpletransitpay.model.EntryRecord;
import com.rumesh.simpletransitpay.model.TripRecord;
import com.rumesh.simpletransitpay.types.TripStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public void execute() {
        final List<EntryRecord> records = csvFileReaderService.read(configProperties.getReadFileName());
        List<TripRecord> tripRecords = new ArrayList<>();
        records.stream()
                .filter(rec -> {
                    final Optional<EntryRecord> optionalEntryRecord = Optional.ofNullable(userCacheService.get(rec.getPan()));
                    if (!optionalEntryRecord.isPresent()) {
                        userCacheService.add(rec.getPan(), rec);
                    }
                    return optionalEntryRecord.isPresent();
                })
                .forEach(rec -> {
                    final EntryRecord startRecord = userCacheService.get(rec.getPan());
                    if (rec.getStopId().equals(startRecord.getStopId())) {
                        tripRecords.add(this.getCalculatedTripRecord(startRecord, rec, TripStatus.CANCELLED));
                        userCacheService.remove(rec.getPan());
                        return;
                    }
                    userCacheService.remove(startRecord.getPan());
                    tripRecords.add(this.getCalculatedTripRecord(startRecord, rec, TripStatus.COMPLETED));
                });
        userCacheService.values()
                .stream()
                .map(entryRecord -> this.getCalculatedTripRecord(entryRecord, null, TripStatus.INCOMPLETE))
                .forEach(tripRecords::add);
        csvFileWriterService.write(tripRecords, configProperties.getWriteFileName());
    }

    public TripRecord getCalculatedTripRecord(@NonNull EntryRecord startRecord,
                                              EntryRecord endRecord,
                                              TripStatus status) {
        TripRecord.TripRecordBuilder recordBuilder = TripRecord.builder()
                .startTime(startRecord.getRecordedTime())
                .endTime(endRecord != null ? endRecord.getRecordedTime() : null)
                .fromStopId(startRecord.getStopId())
                .status(status);
        return Optional.ofNullable(endRecord)
                .map(eRecord -> recordBuilder
                        .endTime(eRecord.getRecordedTime())
                        .duration(Duration.between(eRecord.getRecordedTime(), startRecord.getRecordedTime()).getSeconds())
                        .toStopId(eRecord.getStopId())
                        .chargeAmount(priceListCacheService.getPrice(startRecord.getStopId(), eRecord.getStopId()))
                        .companyId(eRecord.getCompanyId())
                        .busId(eRecord.getBusID())
                        .pan(eRecord.getPan()))
                .orElse(recordBuilder)
                .build();
    }
}
