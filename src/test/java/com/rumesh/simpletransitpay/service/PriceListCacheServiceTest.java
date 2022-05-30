package com.rumesh.simpletransitpay.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class PriceListCacheServiceTest {

    private PriceListCacheService priceListCacheService;

    @BeforeEach
    void setUp() {
        priceListCacheService = new PriceListCacheService();
    }

    @Test
    public void givenThatExistingStopIdsThenSuccessAndReturnsPrice() {
        final Double price = priceListCacheService.getPrice("Stop1", "Stop2");
        assertEquals(3.25, price);
    }

    @Test
    public void givenThatNonExistingStopId1OrStopId2ThenReturnsZero() {
        final Double stopId1NonExistPrice = priceListCacheService.getPrice("Strop1", "Stop2");
        assertEquals(0.0, stopId1NonExistPrice);

        final Double stopId2NonExistPrice = priceListCacheService.getPrice("Stop1", "Strop2");
        assertEquals(0.0, stopId2NonExistPrice);
    }

    @Test
    public void givenThatStopIdsThenReturnsProperFormattedKey() {
        final String key = priceListCacheService.getKey("Stop1", "Stop2");
        assertEquals("Stop1-Stop2", key);
    }

    @Test
    public void givenThatStopIdsAndPriceThenPriceShouldBeInserted() {
        priceListCacheService.setPrice("Stop8", "Stop11" , 15.0);
        final Double price = priceListCacheService.getPrice("Stop8", "Stop11");
        assertEquals(15.0, price);
    }
}
