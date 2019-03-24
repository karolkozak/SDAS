package com.sdas;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Application {

    private final static String RESOURCE_NAMES = "application,twitter";

    public static void main(String[] args) {
        new SpringApplicationBuilder(Application.class)
                .properties("spring.config.name=" + RESOURCE_NAMES)
                .run(args);
    }
}
