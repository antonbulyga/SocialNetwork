package com.senla.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto {
    @NotNull(message = "Role id field is mandatory")
    private Long id;

    @NotBlank(message = "Name is mandatory")
    private String name;

    private List<UserShortDto> users;
}
