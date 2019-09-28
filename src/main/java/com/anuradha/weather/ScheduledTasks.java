package com.anuradha.weather;

import com.anuradha.weather.services.WeatherService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class ScheduledTasks {
    private static final Logger logger = LogManager.getLogger(ScheduledTasks.class);
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Autowired
    private WeatherService weatherService;

    // fires midnight of every 12 hours (twice a day)
    @Scheduled(cron = "0 0 0/12 * * ?")
    public void scheduleTaskWithFixedRate() {
        logger.info("House keep checking is in progress :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()) );
        weatherService.houseKeepData();
    }
}
