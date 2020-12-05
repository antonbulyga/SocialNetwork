package com.senla.entity;

import com.senla.enumeration.Gender;
import lombok.*;
import org.hibernate.annotations.Formula;

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
@Table(name = "user_profile")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Date of birth is mandatory")
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @NotNull(message = "Field gender is mandatory")
    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @NotBlank(message = "Field phone is mandatory")
    @Column(name = "phone")
    private String phoneNumber;

    @NotBlank(message = "Field first name is mandatory")
    @Column(name = "first_name")
    private String firstName;

    @NotBlank(message = "Field last name is mandatory")
    @Column(name = "last_name")
    private String lastName;

    @OneToOne(mappedBy = "profile",
            fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private User user;

    @NotBlank(message = "Field country is mandatory")
    @Column(name = "country")
    private String country;

    @NotBlank(message = "Field city is mandatory")
    @Column(name = "city")
    private String city;

}
