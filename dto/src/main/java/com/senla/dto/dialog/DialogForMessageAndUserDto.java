package com.senla.dto.dialog;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DialogForMessageAndUserDto {
    @NotNull(message = "Id of the dialog is mandatory")
    private long id;

    @NotBlank(message = "Name of the dialog is mandatory")
    private String name;

    private LocalDateTime timeCreation;
}
