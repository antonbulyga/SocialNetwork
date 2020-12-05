package com.senla.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunityShortDto {
    @NotNull(message = "Id of the community is mandatory")
    private long id;

    @NotBlank(message = "Name of the community is mandatory")
    private String name;

}
