package com.senla.dto.profile;

import com.senla.enumeration.Gender;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileForUserDto {
    @NotNull(message = "Profile id field is mandatory")
    private long id;

    @NotBlank(message = "Field first name is mandatory")
    private String firstName;

    @NotNull(message = "Date of birth is mandatory")
    private LocalDate dateOfBirth;

    @NotNull(message = "Field gender is mandatory")
    private Gender gender;

    @NotBlank(message = "Field phone is mandatory")
    private String phoneNumber;

    @NotBlank(message = "Field last name is mandatory")
    private String lastName;

    @NotBlank(message = "Field country is mandatory")
    private String country;

    @NotBlank(message = "Field city is mandatory")
    private String city;


}
