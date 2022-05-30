package com.rumesh.simpletransitpay.service;

import com.rumesh.simpletransitpay.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class MaximumTravelLocationCache {
    private final Map<String, String> maxTravel = new HashMap<>();

    public MaximumTravelLocationCache() {
        maxTravel.put("Stop1","Stop3");
        maxTravel.put("Stop2","Stop3");
        maxTravel.put("Stop3","Stop1");
    }

    public String getMaxTravel(String stopId) {
        log.info("Get max travel location -> StopId : {}", stopId);
        return Optional
                .ofNullable(this.maxTravel.get(stopId))
                .orElseThrow(() -> new NotFoundException("Invalid args: Max travel location not found."));
    }
}
