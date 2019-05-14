package com.sdas.services.startup;

import com.sdas.properties.SdasProperties;
import com.sdas.repositories.TagRepository;
import com.sdas.services.TagStatisticsService;
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
public class TagEntityStartUpCreator {
    private final TagRepository tagRepository;
    private final TagStatisticsService tagStatisticsService;
    private final SdasProperties sdasProperties;

    private static final Logger LOGGER = LoggerFactory.getLogger(SocialMediaDataStartUpFetcher.class);
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    @EventListener(ContextRefreshedEvent.class)
    public void fetchDataAfterStartUp() {
        sdasProperties.getTags().stream().filter(tagName -> tagRepository.findByTagNameEquals(tagName) == null).forEach(tagName -> {
            LOGGER.info("Create tag entity for tag: " + tagName + " after start up :: Execution Time - {}",
                    DATE_TIME_FORMATTER.format(LocalDateTime.now()));
            tagStatisticsService.createTagEntityForTagName(tagName);
            tagStatisticsService.updateStatisticsForTag(tagName);
        });
    }
}
