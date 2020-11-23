package com.senla.model.entity;

import com.senla.model.enumeration.Gender;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user_profile")
public class Profile{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;
    @Column(name = "gender", columnDefinition = "enum('MALE','FEMALE','RETRY')")
    @Basic(optional = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Column(name = "phone", nullable = false)
    private String phoneNumber;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @OneToOne(mappedBy = "profile",
            fetch = FetchType.LAZY)
    private User user;
    @Column(name = "country", nullable = false)
    private String country;
    @Column(name = "city", nullable = false)
    private String city;
}
