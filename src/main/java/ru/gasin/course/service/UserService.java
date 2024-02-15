package ru.gasin.course.service;

import ru.gasin.course.rest.dto.EditUserRequest;
import ru.gasin.course.rest.dto.NewUserRequest;
import ru.gasin.course.rest.dto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> findAllUsers();

    Long addNewUser(NewUserRequest request);

    UserDto findById(Long id);

    void deleteById(Long id);

    UserDto edit(Long id, EditUserRequest request);
}
