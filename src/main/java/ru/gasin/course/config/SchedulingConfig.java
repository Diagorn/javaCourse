package ru.gasin.course.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import ru.gasin.course.config.properties.CronProperties;
import ru.gasin.course.job.SetDefaultAvatarJob;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class SchedulingConfig implements SchedulingConfigurer {


    private final SetDefaultAvatarJob setDefaultAvatarJob;
    private final CronProperties cronProperties;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.addCronTask(setDefaultAvatarJob, cronProperties.getSetDefaultAvatar());
    }
}
