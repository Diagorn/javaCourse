package ru.gasin.course.rest.dto;

import lombok.Data;

@Data
public class EditUserRequest {
    private String email;
    private String username;
    private String avatarUrl;
    private Byte rating;
}
