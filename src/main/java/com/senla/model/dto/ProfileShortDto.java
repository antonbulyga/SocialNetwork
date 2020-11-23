package com.senla.model.dto;

import com.senla.model.enumeration.Gender;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileShortDto {
    private Long id;
    private LocalDate dateOfBirth;
    private Gender gender;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private String country;
    private String city;
}
