package com.senla.model.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostShortDto {
    private Long id;
    private String text;
    private LocalDateTime dateOfCreation;
}
