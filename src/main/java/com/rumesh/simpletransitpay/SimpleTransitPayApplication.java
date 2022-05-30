package com.rumesh.simpletransitpay;

import com.rumesh.simpletransitpay.service.TripRecordService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SimpleTransitPayApplication {

    private final TripRecordService tripRecordService;

    public SimpleTransitPayApplication(TripRecordService tripRecordService) {
        this.tripRecordService = tripRecordService;
    }

    public static void main(String[] args) {
        SpringApplication.run(SimpleTransitPayApplication.class, args);
    }

    @Bean
    public void startCalculation() {
        tripRecordService.executeTripCostCalculation();
    }
}
