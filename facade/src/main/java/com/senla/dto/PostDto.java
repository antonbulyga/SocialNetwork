package com.senla.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    @NotNull(message = "Post id is mandatory")
    private Long id;

    private List<LikeShortDto> likes;

    @NotBlank(message = "Field text mandatory")
    private String text;

    private UserShortDto user;

    private CommunityShortDto community;

    @NotNull(message = "Creation time field is mandatory")
    private LocalDateTime dateOfCreation;
}
