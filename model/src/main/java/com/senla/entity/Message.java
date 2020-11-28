package com.senla.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

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

    @NotBlank(message = "Message is mandatory")
    @Column(name = "message")
    private String message;

    @ManyToOne(targetEntity = Dialog.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "dialog_id")
    private Dialog dialog;

    @NotNull(message = "Creation time is mandatory")
    @Column(name = "creation_time")
    private LocalDateTime creationTime;

}
