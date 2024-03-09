package ru.gasin.course.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import ru.gasin.course.entity.User;
import ru.gasin.course.exception.exceptions.BadRequestException;
import ru.gasin.course.exception.exceptions.EmptyFieldsException;
import ru.gasin.course.exception.exceptions.NotFoundException;
import ru.gasin.course.exception.exceptions.WeakPasswordException;
import ru.gasin.course.repo.UserRepo;
import ru.gasin.course.rest.dto.EditUserRequest;
import ru.gasin.course.rest.dto.NewUserRequest;
import ru.gasin.course.rest.dto.RegistrationRequest;
import ru.gasin.course.rest.dto.UserDto;
import ru.gasin.course.service.UserService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UserDto> findAllUsers() {
        List<User> all = userRepo.findAll();

        return all.stream()
                .map(user -> UserDto.builder()
                        .email(user.getEmail())
                        .avatarUrl(user.getAvatarUrl())
                        .username(user.getUsername())
                        .rating(user.getRating())
                        .postsCount(CollectionUtils.isEmpty(user.getPosts()) ? 0 : user.getPosts().size())
                        .commentsCount(CollectionUtils.isEmpty(user.getComments()) ? 0 : user.getComments().size())
                        .build())
                .toList();
    }

    @Override
    public Long addNewUser(NewUserRequest request) {

        if (!StringUtils.hasText(request.getEmail()) ||
                !StringUtils.hasText(request.getUsername()) ||
                !StringUtils.hasText(request.getPassword())) {
            throw new EmptyFieldsException("Некорректные поля в запросе - необходимо заполнить необходимые");
        }

        if (request.getPassword().length() < 8) {
            throw new WeakPasswordException("Пароль слишком слабый - необходимо как минимум 8 знаков");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setCreatedAt(LocalDate.now());
        user.setRating((byte) 5);

        user = userRepo.save(user);

        return user.getId();
    }

    @Override
    public UserDto findById(Long id) {
        Optional<User> userFromDb = userRepo.findById(id);

        if (userFromDb.isEmpty()) {
            throw new NotFoundException("Не найден пользователь с id = " + id);
        }

        User user = userFromDb.get();
        return buildDto(user);
    }

    @Override
    public void deleteById(Long id) {
        if (!userRepo.existsById(id)) {
            throw new NotFoundException("Не найден пользователь с id = " + id);
        }

        userRepo.deleteById(id);
    }

    @Override
    public UserDto edit(Long id, EditUserRequest request) {
        Optional<User> userFromDb = userRepo.findById(id);

        if (userFromDb.isEmpty()) {
            throw new NotFoundException("Не найден пользователь с id = " + id);
        }

        if (!StringUtils.hasText(request.getEmail()) ||
                !StringUtils.hasText(request.getUsername())) {
            throw new EmptyFieldsException("Некорректные поля в запросе - необходимо заполнить необходимые");
        }

        User user = userFromDb.get();

        user.setRating(request.getRating());
        user.setAvatarUrl(request.getAvatarUrl());
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());

        userRepo.save(user);

        return buildDto(user);
    }

    @Override
    public void register(RegistrationRequest request) {
        if (!StringUtils.hasText(request.getUsername())
                || userRepo.existsByUsername(request.getUsername())) {
            throw new BadRequestException("Невалидный логин");
        }

        if (!StringUtils.hasText(request.getPassword())
                || request.getPassword().length() < 8) {
            throw new WeakPasswordException("Пароль слишком слабый");
        }

        String password = passwordEncoder.encode(request.getPassword());

        User user = new User();
        user.setPassword(password);
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        user.setCreatedAt(LocalDate.now());
        user.setRating((byte) 5);

        userRepo.save(user);
    }

    private UserDto buildDto(User user) {
        return UserDto.builder()
                .email(user.getEmail())
                .avatarUrl(user.getAvatarUrl())
                .username(user.getUsername())
                .rating(user.getRating())
                .postsCount(user.getPosts().size())
                .commentsCount(user.getComments().size())
                .build();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Не удалось загрузить пользователя с логином " + username));
    }
}
