package com.senla.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DialogShortDto {
    private Long id;
    private String name;
    private LocalDate timeCreation;
}
