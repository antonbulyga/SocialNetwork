package com.senla.model.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageShortDto {
    private Long id;
    private String message;
    private LocalDate creationTime;
}
