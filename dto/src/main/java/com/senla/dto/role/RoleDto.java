package com.senla.dto.role;

import com.senla.dto.user.UserNestedDto;
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
    private long id;

    @NotBlank(message = "Name is mandatory")
    private String name;

    private List<UserNestedDto> users;
}
