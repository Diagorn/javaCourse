package ru.gasin.course.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "theme")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Theme extends AbstractEntity {
    @Column(name = "name", length = 50, unique = true)
    private String name;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "theme")
    private List<Post> posts;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role_theme",
            joinColumns = @JoinColumn(name = "theme_id"),
            inverseJoinColumns = @JoinColumn(name = "user_role_id")
    )
    private List<Role> roles;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "theme_tag",
            joinColumns = @JoinColumn(name = "id_tag"),
            inverseJoinColumns = @JoinColumn(name = "theme_id")
    )
    private List<Tag> tags;
}
