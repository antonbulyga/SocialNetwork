package com.senla.dto;

import com.senla.enumeration.Gender;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDto {
    private Long id;
    private LocalDate dateOfBirth;
    private Gender gender;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private UserShortDto user;
    private String country;
    private String city;
}
