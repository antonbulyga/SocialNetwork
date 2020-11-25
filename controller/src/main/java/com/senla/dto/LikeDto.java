package com.senla.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LikeDto {
    private Long id;
    private PostShortDto post;
    private UserShortDto user;
}
