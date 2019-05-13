package com.sdas.services.crons;

import com.sdas.services.TwitterDataFetcher;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class SocialMediaDataFetcherScheduler {
    private final TwitterDataFetcher twitterDataFetcher;

    private static final Logger LOGGER = LoggerFactory.getLogger(SocialMediaDataFetcherScheduler.class);
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
    @Value("sdas.dataProvider")
    private static String dataProvider;

    @Scheduled(cron = "${sdas.cron.fetcher}")
    public void executeSendRateReminderTask() {
        logExecution();
        twitterDataFetcher.fetchData();
    }

    private void logExecution() {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Fetching" + dataProvider + " Task :: Execution Time - {}", DATE_TIME_FORMATTER.format(LocalDateTime.now()));
        }
    }
}
