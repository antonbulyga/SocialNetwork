package com.senla.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostShortDto {
    @NotNull(message = "Post id is mandatory")
    private long id;

    @NotBlank(message = "Field text mandatory")
    private String text;

    @NotNull(message = "Creation time field is mandatory")
    private LocalDateTime dateOfCreation;
}
