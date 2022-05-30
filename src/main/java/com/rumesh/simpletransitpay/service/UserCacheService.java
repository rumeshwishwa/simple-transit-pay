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

    private final Map<String, EntryRecord> userCache = new HashMap<>();

    public void add(Long pan, String busId, EntryRecord entryRecord) {
        this.userCache.put(getKey(pan,busId), entryRecord);
    }

    public EntryRecord get(Long pan, String busId) {
        return this.userCache.get(getKey(pan,busId));
    }

    public void remove(Long pan, String busId) {
        this.userCache.remove(getKey(pan,busId));
    }

    public Collection<EntryRecord> values() {
        return userCache.values();
    }

    private String getKey(Long pan,String busId) {
        return pan.toString()+busId;
    }
}
