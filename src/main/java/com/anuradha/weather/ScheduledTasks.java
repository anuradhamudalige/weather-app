package com.anuradha.weather;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class ScheduledTasks {
    private static final Logger logger = LogManager.getLogger(ScheduledTasks.class);
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    // fires midnight of every 3 days
    @Scheduled(cron = "0 0 */3 * * ?")
    public void scheduleTaskWithFixedRate() {
        logger.info("House keeping is in progress :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()) );
    }
}
