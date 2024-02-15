package ru.gasin.course.rest.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NewUserRequest {
    private String email;
    private String username;
    private String password;
}
