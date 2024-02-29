package ru.gasin.course.job;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.gasin.course.repo.UserRepo;

@Component
@RequiredArgsConstructor
public class SetDefaultAvatarJob implements Runnable {

    private static final String DEFAULT_AVATAR_URL = "https://res.cloudinary.com/startup-grind/image/upload/c_fill,dpr_2.0,f_auto,g_center,h_1080,q_100,w_1080/v1/gcs/platform-data-dsc/events/23cd80bb785b4513d1a592a46bd882a4_JQJVhck.jpg";

    private final UserRepo userRepo;

    @Override
    public void run() {
        userRepo.findAllByAvatarUrlIsNull()
                .forEach(user -> {
                    user.setAvatarUrl(DEFAULT_AVATAR_URL);
                    userRepo.save(user);
                });
    }
}
