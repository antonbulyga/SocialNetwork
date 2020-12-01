package com.senla.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DialogShortDto {
    @NotNull(message = "Id of the dialog is mandatory")
    private long id;

    @NotBlank(message = "Name of the dialog is mandatory")
    private String name;

    @NotNull(message = "Time creation field is mandatory")
    private LocalDateTime timeCreation;
}
