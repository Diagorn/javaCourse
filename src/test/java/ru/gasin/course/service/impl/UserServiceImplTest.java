package ru.gasin.course.service.impl;

import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.gasin.course.entity.User;
import ru.gasin.course.exception.exceptions.EmptyFieldsException;
import ru.gasin.course.repo.UserRepo;
import ru.gasin.course.rest.dto.NewUserRequest;
import ru.gasin.course.service.UserService;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

class UserServiceImplTest {

    private static String USERNAME = "Diagorn";
    private static String EMAIL = "some@mail.ru";
    private static String PASSWORD = "Password_123";
    private static String WRONG_FIELDS = "Некорректные поля в запросе - необходимо заполнить необходимые";

    private UserService userService;

    @Mock
    private UserRepo userRepo;

    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.userService = new UserServiceImpl(userRepo);
    }

    @Test
    @DisplayName("Проверка успешного добавления пользователя")
    public void itShouldSuccessfullySaveNewUser() {
        // given
        NewUserRequest request = NewUserRequest.builder()
                .username(USERNAME)
                .email(EMAIL)
                .password(PASSWORD)
                .build();

        User expectedUser = User.builder()
                .rating((byte) 5)
                .username(USERNAME)
                .password(PASSWORD)
                .email(EMAIL)
                .createdAt(LocalDate.now())
                .build();
        expectedUser.setId(1L);

        given(userRepo.save(any(User.class)))
                .willReturn(expectedUser);

        // when
        Long newId = userService.addNewUser(request);

        // then
        then(userRepo).should().save(userArgumentCaptor.capture());
        User capturedUser = userArgumentCaptor.getValue();

        then(userRepo).shouldHaveNoMoreInteractions();

        assertThat(capturedUser).isNotNull();
        assertThat(capturedUser)
                .isEqualTo(expectedUser);

        assertThat(newId).isPositive();
    }

    @Test
    @DisplayName("Проверка ошибки при добавлении пользователя без логина")
    public void isShouldFailToSaveNewUserWithNullUsername() {
        // given
        NewUserRequest request = NewUserRequest.builder()
                .username(null)
                .email(EMAIL)
                .password(PASSWORD)
                .build();

        // when
        ThrowableAssert.ThrowingCallable callable = () -> userService.addNewUser(request);

        // then
        assertThatThrownBy(callable)
                .isInstanceOf(EmptyFieldsException.class)
                .hasMessage(WRONG_FIELDS);

        then(userRepo).shouldHaveNoInteractions();
    }
}