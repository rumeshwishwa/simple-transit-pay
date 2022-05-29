package com.rumesh.simpletransitpay.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Configuration class that bridge the properties file details in to application
 *
 * @author Rumesh
 */

@Data
@Component
public class ConfigProperties {

    @Value("${file.read.name}")
    private String readFileName;

    @Value("${file.write.name}")
    private String writeFileName;
}
