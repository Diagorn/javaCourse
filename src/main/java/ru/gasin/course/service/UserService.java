package ru.gasin.course.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.gasin.course.rest.dto.EditUserRequest;
import ru.gasin.course.rest.dto.NewUserRequest;
import ru.gasin.course.rest.dto.RegistrationRequest;
import ru.gasin.course.rest.dto.UserDto;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<UserDto> findAllUsers();

    /**
     * Добавление нового пользователя через админ-панель
     *
     * @param request - запрос на добавление
     * @return - id добавленного пользователя
     */
    Long addNewUser(NewUserRequest request);

    UserDto findById(Long id);

    void deleteById(Long id);

    UserDto edit(Long id, EditUserRequest request);

    void register(RegistrationRequest request);
}
