package com.sdas.services.startup;

import com.sdas.services.TwitterDataFetcher;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class SocialMediaDataStartUpFetcher {
    private final TwitterDataFetcher twitterDataFetcher;

    private static final Logger LOGGER = LoggerFactory.getLogger(SocialMediaDataStartUpFetcher.class);
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    @EventListener(ContextRefreshedEvent.class)
    public void fetchDataAfterStartUp() {
        LOGGER.info("Fetch social data after start up :: Execution Time - {}",
                DATE_TIME_FORMATTER.format(LocalDateTime.now()));
        this.twitterDataFetcher.fetchData();
    }
}
