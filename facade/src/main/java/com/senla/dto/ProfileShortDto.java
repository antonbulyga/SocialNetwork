package com.senla.dto;

import com.senla.enumeration.Gender;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileShortDto {
    @NotNull(message = "Profile id field is mandatory")
    private Long id;

    @NotNull(message = "Date of birth is mandatory")
    private LocalDateTime dateOfBirth;

    @NotNull(message = "Field gender is mandatory")
    private Gender gender;

    @NotBlank(message = "Field phone is mandatory")
    private String phoneNumber;

    @NotBlank(message = "Field first name is mandatory")
    private String firstName;

    @NotBlank(message = "Field last name is mandatory")
    private String lastName;

    @NotBlank(message = "Field country is mandatory")
    private String country;

    @NotBlank(message = "Field city is mandatory")
    private String city;
}
