package com.senla.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Like> likes;

    @NotBlank(message = "Text of the post is mandatory")
    @Column(name = "text")
    private String text;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private User user;

    @NotNull(message = "Date of creation is mandatory")
    @Column(name = "date_of_creation")
    private LocalDateTime dateOfCreation;

    @ManyToOne(targetEntity = Community.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "community_id")
    private Community community;

}
