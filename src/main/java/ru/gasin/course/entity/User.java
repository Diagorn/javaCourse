package ru.gasin.course.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "usr")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class User extends AbstractEntity {
    @Column(name = "email", nullable = false, length = 150)
    private String email;
    @Column(name = "password", nullable = false, length = 150)
    private String password;
    @Column(name = "username", nullable = false, length = 100)
    private String username;
    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;
    @Column(name = "description", length = 512)
    private String description;
    @Column(name = "avatar_url", length = 200)
    private String avatarUrl;
    @Column(name = "rating")
    private Byte rating;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "author", cascade = CascadeType.ALL)
    private List<Post> posts;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "author", cascade = CascadeType.ALL)
    private List<Comment> comments;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_user_role",
            joinColumns = @JoinColumn(name = "user_role_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<Role> roles;
}
