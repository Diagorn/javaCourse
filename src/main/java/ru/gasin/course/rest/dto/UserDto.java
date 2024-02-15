package ru.gasin.course.rest.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
    private String email;
    private String username;
    private String avatarUrl;
    private Byte rating;
    private Integer postsCount;
    private Integer commentsCount;
}
