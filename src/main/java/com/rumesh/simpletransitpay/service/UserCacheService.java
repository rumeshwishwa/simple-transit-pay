package com.rumesh.simpletransitpay.service;

import com.rumesh.simpletransitpay.model.EntryRecord;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserCacheService {

    private Map<Long, EntryRecord> userCache = new HashMap<>();

    public void add(Long pan, EntryRecord entryRecord) {
        this.userCache.put(pan,entryRecord);
    }

    public EntryRecord get(Long pan) {
        return this.userCache.get(pan);
    }

    public void remove(Long pan) {
        this.userCache.remove(pan);
    }

    public Collection<EntryRecord> values() {
        return userCache.values();
    }
}
