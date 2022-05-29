package com.rumesh.simpletransitpay.service;

import com.rumesh.simpletransitpay.types.FileType;

import java.util.List;

public interface FileReaderService<T> {

    List<T> read(String file);
    boolean isApplicable(FileType fileType);

}
