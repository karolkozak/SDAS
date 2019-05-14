package com.sdas.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "sdas")
public class SdasProperties {
    private Map<String, String> cron;
    private String dataProvider;
    private List<String> tags;
}
