package com.senla.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserShortDto {
    @NotNull(message = "Field id is mandatory")
    private long id;

   /* @NotBlank(message = "Email is mandatory")
    private String email;

    @NotBlank(message = "User name is mandatory")
    private String userName;

    @NotBlank(message = "Password is mandatory")
    private String password;

    @NotNull(message = "Creation time field is is mandatory")
    private LocalDateTime creationTime;*/
}
