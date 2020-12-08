package com.senla.dto.post;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostForUserAndCommunityDto {
    @NotNull(message = "Post id is mandatory")
    private long id;

    @NotBlank(message = "Field text mandatory")
    private String text;

    private LocalDateTime dateOfCreation;
}
