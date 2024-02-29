package ru.gasin.course.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "application.crons")
public class CronProperties {
    private String setDefaultAvatar;
    private String deleteUnwantedPosts;
}
