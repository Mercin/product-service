package com.example.productservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "fixer")
@Data
public class FixerConfig {

    private String endpoint;
    private String apiKey;
}
