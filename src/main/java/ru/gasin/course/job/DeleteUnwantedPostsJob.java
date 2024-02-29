package ru.gasin.course.job;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.gasin.course.repo.PostRepo;

@Component
@RequiredArgsConstructor
public class DeleteUnwantedPostsJob {

    private final PostRepo postRepo;

    @Scheduled(cron = "${application.crons.setDefaultAvatar}")
    public void doDelete() {
        postRepo.deleteAll(postRepo.findAllByThumbnailUrlIsNull());
    }
}
