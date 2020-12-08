package com.senla.dto.role;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleForProfileDto {
    @NotNull(message = "Role id field is mandatory")
    private long id;

    @NotBlank(message = "Name is mandatory")
    private String name;
}
