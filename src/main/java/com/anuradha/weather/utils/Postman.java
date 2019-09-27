package com.anuradha.weather.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class Postman {
    private static final Logger logger = LogManager.getLogger(Postman.class);

    public static ResponseEntity<String> get(String resourceUrl) {
        logger.info(resourceUrl);
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForEntity(resourceUrl, String.class);
    }
}
