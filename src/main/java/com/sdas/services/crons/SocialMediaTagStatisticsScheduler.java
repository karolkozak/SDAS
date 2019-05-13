package com.sdas.services.crons;

import com.sdas.services.TagStatisticsService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SocialMediaTagStatisticsScheduler {
    private final TagStatisticsService tagStatisticsService;

    private static final Logger LOGGER = LoggerFactory.getLogger(SocialMediaDataFetcherScheduler.class);
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
    @Value("sdas.dataProvider")
    private static String dataProvider;
    @Value("sdas.tags")
    private static List<String> tags;

    @Scheduled(cron = "${sdas.cron.statistics}")
    public void executeSendRateReminderTask() {
        tags.forEach(tagName -> {
            logExecution(tagName);
            tagStatisticsService.updateStatisticsForTag(tagName);
        });
    }

    private void logExecution(String tagName) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info(dataProvider + " tag: " + tagName + " Task :: Execution Time - {}", DATE_TIME_FORMATTER.format(LocalDateTime.now()));
        }
    }
}
