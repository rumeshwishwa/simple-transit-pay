package com.rumesh.simpletransitpay.converter;

import com.rumesh.simpletransitpay.config.DateTimeFormats;
import com.rumesh.simpletransitpay.model.TripRecord;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class TripRecordStringConverter implements StringConvertable<TripRecord> {

    /**
     * Returns the string array based on the current properties of TripRecord
     * values in the array ordered based on below format order
     * Started, Finished, DurationSecs, FromStopId, ToStopId, ChargeAmount, CompanyId, BusID, PAN,Status
     *
     * @param record TripRecord that need to be converted as String[]
     * @return String[]
     */
    @Override
    public String[] convert(TripRecord record) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DateTimeFormats.DD_MM_YYYY_HH_MM_SS);
        return new String[]{
                record.getStartTime() != null ? record.getStartTime().format(formatter) : "",
                record.getEndTime() != null ? record.getEndTime().format(formatter) : "",
                String.valueOf(record.getDuration()),
                record.getFromStopId(),
                record.getToStopId(),
                String.valueOf(record.getChargeAmount()),
                record.getCompanyId(),
                record.getBusId(),
                String.valueOf(record.getPan()),
                record.getStatus().name()
        };
    }
}
