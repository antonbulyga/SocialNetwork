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
@EqualsAndHashCode(of = "id")
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email
    @Column(name = "email")
    private String email;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "password")
    private String password;

    @OneToOne(fetch = FetchType.EAGER, cascade = {
            CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH,CascadeType.REMOVE})
    @JoinColumn(name = "user_profile_id", referencedColumnName = "id")
    private Profile profile;

    @ManyToMany(mappedBy = "users", fetch = FetchType.LAZY)
    private List<Community> communities;

    @ManyToMany(mappedBy = "users", fetch = FetchType.EAGER)
    private Set<Role> roles;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH,CascadeType.REMOVE})
    private List<Post> posts;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH,CascadeType.REMOVE})
    private List<Message> messages;

    @OneToMany(mappedBy = "user",
            fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH,CascadeType.REMOVE})
    private List<Like> likes;

    @Column(name = "creation_time", nullable = false)
    private LocalDateTime creationTime;

    @OneToMany(mappedBy = "adminUser",
            fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH})
    private List<Community> communitiesWhereUserAdmin;

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
