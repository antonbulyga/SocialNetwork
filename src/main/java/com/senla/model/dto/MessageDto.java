package com.senla.model.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto {
    private Long id;
    private UserShortDto user;
    private String message;
    private DialogShortDto dialog;
    private LocalDate creationTime;

}
