package ru.gasin.course.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "post")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Post extends AbstractEntity {
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @Column(name = "heading", length = 50, nullable = false)
    private String heading;
    @Column(name = "thumbnail_url", length = 200)
    private String thumbnailUrl;
    @Column(name = "likes_amount", nullable = false)
    private Integer likesAmount;
    @Column(name = "post_text", nullable = false)
    private String text;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "theme_id")
    private Theme theme;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "post_type_id")
    private PostType postType;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id")
    private List<Comment> comments;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "tag_post",
            inverseJoinColumns = @JoinColumn(name = "id_tag"),
            joinColumns = @JoinColumn(name = "post_id")
    )
    private List<Tag> tags;
}
