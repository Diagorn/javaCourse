package ru.gasin.course.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "post_type")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostType extends AbstractEntity {
    @Column(name = "name", length = 50, unique = true)
    private String name;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "postType")
    private List<Post> posts;
}
