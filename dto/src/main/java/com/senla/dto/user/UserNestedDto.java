package com.senla.dto.user;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserNestedDto {
    @NotNull(message = "Field id is mandatory")
    private long id;

    @NotBlank(message = "User name is mandatory")
    private String userName;

}
