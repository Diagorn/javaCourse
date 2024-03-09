package ru.gasin.course.rest.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.WebApplicationContext;
import ru.gasin.course.repo.UserRepo;
import ru.gasin.course.rest.dto.NewUserRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private UserController userController;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(value = "Admin", authorities = {"USER", "ADMIN"})
    @Sql(value = {"/sql/before/add-roles.sql", "/sql/before/insert-default-admin.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void itShouldAddNewUser() throws Exception {
        // given
        // new user request
        String username = "Diagorn99";
        NewUserRequest request = NewUserRequest.builder()
                .username(username)
                .password("Turing2024")
                .email("some@mail.ru")
                .build();

        // when
        ResultActions postNewUserActions = mockMvc.perform(post("/api/v1/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(javaToJson(request))
        );

        // Then
        postNewUserActions.andExpect(status().is2xxSuccessful());
        assertThat(userRepo.findByUsername(username)).isPresent()
                .hasValueSatisfying(u -> {
                    assertThat(u.getEmail().equals(request.getEmail()));
                    assertThat(u.getUsername().equals(request.getUsername()));
                    assertThat(!u.getPassword().isBlank());
                });
    }

    private String javaToJson(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            fail("Failed java to json");
            return null;
        }
    }
}