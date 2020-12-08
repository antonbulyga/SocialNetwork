package com.senla.dto.like;

import com.senla.dto.user.UserNestedDto;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LikeForPostAndUserDto {
    @NotNull(message = "Like id is mandatory")
    private long id;

    private UserNestedDto userNestedDto;
}
