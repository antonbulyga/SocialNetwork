package com.senla.dto;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequestDto {
    @NotBlank(message = "Username is mandatory")
    private String username;

    @Min(value = 6, message = "Password should be not less than 6 characters")
    @Max(value = 20, message = "Password should be not greater than 20 characters")
    @NotBlank(message = "Password is mandatory")
    private String password;

}
