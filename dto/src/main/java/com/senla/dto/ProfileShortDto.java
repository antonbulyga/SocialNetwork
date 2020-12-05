package com.senla.dto;

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
public class ProfileShortDto {
    @NotNull(message = "Profile id field is mandatory")
    private long id;

    @NotBlank(message = "Field first name is mandatory")
    private String firstName;

}
