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
public class MessageDto {
    @NotNull(message = "Message id is mandatory")
    private long id;

    private UserShortDto user;

    @NotBlank(message = "Text of the message is mandatory")
    private String message;

    private DialogShortDto dialog;

    private LocalDateTime creationTime;

}
