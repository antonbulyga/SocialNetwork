package com.senla.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LikeDto {
    @NotNull(message = "Like id is mandatory")
    private long id;

    private PostShortDto post;

    private UserShortDto user;
}
