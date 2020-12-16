package com.senla.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "dialogs")
public class Dialog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "time_creation")
    private LocalDateTime timeCreation;

    @OneToMany(mappedBy = "dialog", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Message> messages;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE
    })
    @JoinTable(name = "dialogs_has_users",
            joinColumns = {@JoinColumn(name = "dialogs_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "users_id", referencedColumnName = "id")})
    private List<User> userList;

    public Dialog(Long id, String name, LocalDateTime timeCreation) {
        this.id = id;
        this.name = name;
        this.timeCreation = timeCreation;
    }
}
