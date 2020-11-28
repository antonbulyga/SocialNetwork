package com.senla.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DialogDto {
    @NotNull(message = "Id of the dialog is mandatory")
    private Long id;

    @NotBlank(message = "Name of the dialog is mandatory")
    private String name;

    @NotNull(message = "Time creation field is mandatory")
    private LocalDateTime timeCreation;

    private List<MessageShortDto> messages;

    private List<UserShortDto> userList;

}
