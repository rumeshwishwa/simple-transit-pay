package com.rumesh.simpletransitpay.service;

import com.rumesh.simpletransitpay.model.EntryRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Responsible for cache Passenger entry details until it is processed.
 * This can be proper cache implementation like redis or hazelcast
 * instead of simple map in a production
 *
 * @author Rumesh
 */

@Slf4j
@Service
public class UserCacheService {

    private final Map<Long, EntryRecord> userCache = new HashMap<>();

    public void add(Long pan, EntryRecord entryRecord) {
        this.userCache.put(pan, entryRecord);
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
