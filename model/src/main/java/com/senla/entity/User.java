package com.senla.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
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

    @Min(value = 6, message = "Password should be not less than 6 characters")
    @Max(value = 20, message = "Password should be not greater than 20 characters")
    @NotBlank(message = "Password is mandatory")
    @Column(name = "password")
    private String password;


    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_profile_id", referencedColumnName = "id")
    private Profile profile;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE
    })

    @JoinTable(name = "community_has_users",
            joinColumns = {@JoinColumn(name = "users_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "community_id", referencedColumnName = "id")})
    private List<Community> communities;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {
            CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE
    })
    @JoinTable(name = "users_has_roles",
            joinColumns = {@JoinColumn(name = "users_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "roles_id", referencedColumnName = "id")})
    private Set<Role> roles;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Post> posts;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Message> messages;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private List<Like> likes;
    @NotBlank(message = "Creation time field is is mandatory")
    @Column(name = "creation_time", nullable = false)
    private LocalDateTime creationTime;

    @OneToOne(mappedBy = "adminUser",
            fetch = FetchType.LAZY)
    private Community community;

    @ManyToMany(mappedBy = "userList", fetch = FetchType.LAZY)
    private List<Dialog> dialogs;


}
