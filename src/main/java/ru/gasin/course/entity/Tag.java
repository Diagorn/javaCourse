package ru.gasin.course.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "tag")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Tag extends AbstractEntity {
    @Column(name = "name", length = 50, unique = true)
    private String name;
    @ManyToOne(fetch = FetchType.EAGER)
    private Tag parent;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "parent")
    private List<Tag> children;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "tag_post",
            inverseJoinColumns = @JoinColumn(name = "id_tag"),
            joinColumns = @JoinColumn(name = "post_id")
    )
    private List<Post> posts;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "theme_tag",
            inverseJoinColumns = @JoinColumn(name = "id_tag"),
            joinColumns = @JoinColumn(name = "theme_id")
    )
    private List<Theme> themes;
}
