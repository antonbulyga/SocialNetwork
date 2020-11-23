package com.senla.model.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "message", nullable = false)
    private String message;
    @ManyToOne(targetEntity = Dialog.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "dialog_id")
    private Dialog dialog;
    @Column(name = "creation_time", nullable = false)
    private LocalDate creationTime;

}
