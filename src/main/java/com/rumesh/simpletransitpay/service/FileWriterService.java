package com.rumesh.simpletransitpay.service;

import com.rumesh.simpletransitpay.model.TripRecord;
import com.rumesh.simpletransitpay.types.FileType;

import java.util.List;

public interface FileWriterService<T> {

    void write(List<TripRecord> tripRecords,String file);
    boolean isApplicable(FileType fileType);

}
