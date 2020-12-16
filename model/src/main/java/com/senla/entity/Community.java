package com.senla.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "community")
public class Community {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE
    })
    @JoinTable(name = "community_has_users",
            joinColumns = {@JoinColumn(name = "community_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "users_id", referencedColumnName = "id")})
    private List<User> users;

    @Column(name = "name")
    private String name;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id", referencedColumnName = "id")
    private User adminUser;

    @OneToMany(mappedBy = "community", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Post> posts;

    public Community(Long id, @NotBlank(message = "Name is mandatory") String name) {
        this.id = id;
        this.name = name;
    }
}
