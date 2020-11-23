package com.senla.model.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DialogDto {
    private Long id;
    private String name;
    private LocalDate timeCreation;
    private List<MessageShortDto> messages;
    private List<UserShortDto> userList;

}
