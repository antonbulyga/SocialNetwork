package com.senla.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Email is mandatory")
    @Email
    @Column(name = "email")
    private String email;

    @NotBlank(message = "User name is mandatory")
    @Column(name = "user_name")
    private String userName;


    @NotBlank(message = "Password is mandatory")
    @Column(name = "password")
    private String password;


    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_profile_id", referencedColumnName = "id")
    private Profile profile;

    @ManyToMany(mappedBy = "users", fetch = FetchType.LAZY)
    private List<Community> communities;

    @ManyToMany(mappedBy = "users", fetch = FetchType.EAGER)
    private Set<Role> roles;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Post> posts;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Message> messages;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private List<Like> likes;
    @NotNull(message = "Creation time field is is mandatory")
    @Column(name = "creation_time", nullable = false)
    private LocalDateTime creationTime;

    @OneToOne(mappedBy = "adminUser",
            fetch = FetchType.LAZY)
    private Community community;

    @ManyToMany(mappedBy = "userList", fetch = FetchType.LAZY)
    private List<Dialog> dialogs;

    public User(Long id, @NotBlank(message = "Email is mandatory") @Email String email, @NotBlank(message = "User name is mandatory") String userName, @NotBlank(message = "Password is mandatory") String password,
                @NotNull(message = "Creation time field is is mandatory") LocalDateTime creationTime) {
        this.id = id;
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.creationTime = creationTime;
    }
}
