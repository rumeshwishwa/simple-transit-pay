package com.rumesh.simpletransitpay.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


/**
 * Act as a cache for the price list.
 * Will be loaded prices for each journey starting point and end point.
 *
 * @author Rumesh
 */

@Service
public class PriceListCacheService {

    private final Map<String, Double> priceList = new HashMap<>();

    public PriceListCacheService() {
        priceList.put("Stop1-Stop2", 3.25);
        priceList.put("Stop2-Stop3", 5.50);
        priceList.put("Stop1-Stop3", 7.30);
        priceList.put("Stop2-Stop1", 3.25);
        priceList.put("Stop3-Stop2", 5.50);
        priceList.put("Stop3-Stop1", 7.30);
    }

    /**
     * Returns the cost of the journey based on the declared
     * journey starting point and end point.
     *
     * @param stopId1 user's journey starting point
     * @param stopId2 user's journey ending point
     * @return cost of the journey
     */
    public Double getPrice(String stopId1, String stopId2) {
        return Optional
                .ofNullable(this.priceList.get(getKey(stopId1, stopId2)))
                .orElse(0.0);
    }

    /**
     * Insert the price value from journey starting point and end point
     * in to price list cache.
     *
     * @param stopId1 user's journey starting point
     * @param stopId2 user's journey ending point
     * @param price   price between param 1 and param 2
     */
    public void setPrice(String stopId1, String stopId2, Double price) {
        this.priceList.put(getKey(stopId1, stopId2), price);
    }

    /**
     * Returns the key based on the passing starting point and end point.
     *
     * @param stopId1 user's journey starting point
     * @param stopId2 user's journey ending point
     * @return key
     */
    public String getKey(String stopId1, String stopId2) {
        return stopId1 + "-" + stopId2;
    }
}
