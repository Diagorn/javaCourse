package ru.gasin.course.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "user_role")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Role extends AbstractEntity {
    @Column(name = "name", length = 50, unique = true)
    private String name;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_user_role",
            inverseJoinColumns = @JoinColumn(name = "user_role_id"),
            joinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> users;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role_theme",
            inverseJoinColumns = @JoinColumn(name = "theme_id"),
            joinColumns = @JoinColumn(name = "user_role_id")
    )
    private List<Theme> themes;
}
