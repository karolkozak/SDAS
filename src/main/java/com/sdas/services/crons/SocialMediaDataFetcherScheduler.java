package com.sdas.services.crons;

import com.sdas.properties.SdasProperties;
import com.sdas.services.TwitterDataFetcher;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class SocialMediaDataFetcherScheduler {
    private final TwitterDataFetcher twitterDataFetcher;
    private final SdasProperties sdasProperties;

    private static final Logger LOGGER = LoggerFactory.getLogger(SocialMediaDataFetcherScheduler.class);
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Scheduled(cron = "${sdas.cron.fetcher}")
    public void executeSendRateReminderTask() {
        logExecution();
        twitterDataFetcher.fetchData();
    }

    private void logExecution() {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Fetching" + sdasProperties.getDataProvider() + " Task :: Execution Time - {}", DATE_TIME_FORMATTER.format(LocalDateTime.now()));
        }
    }
}
