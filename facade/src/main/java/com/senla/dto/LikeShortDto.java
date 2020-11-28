package com.senla.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LikeShortDto {
    @NotNull(message = "Like id is mandatory")
    private Long id;
}
