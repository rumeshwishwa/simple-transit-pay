package com.rumesh.simpletransitpay;

import com.rumesh.simpletransitpay.service.TripRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SimpleTransitPayApplication {

    @Autowired
    private TripRecordService tripRecordService;

    public static void main(String[] args) {
        SpringApplication.run(SimpleTransitPayApplication.class, args);
    }

    @Bean
    public void startCalculation() {
        tripRecordService.execute();
    }
}
