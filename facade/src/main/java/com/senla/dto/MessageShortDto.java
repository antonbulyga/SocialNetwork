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
public class MessageShortDto {
    @NotNull(message = "Message id is mandatory")
    private Long id;

    @NotBlank(message = "Text of the message is mandatory")
    private String message;

    @NotNull(message = "Creation time field is mandatory")
    private LocalDateTime creationTime;
}
