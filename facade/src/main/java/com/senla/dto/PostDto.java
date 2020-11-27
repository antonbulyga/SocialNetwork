package com.senla.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    private Long id;
    private List<LikeShortDto> likes;
    private String text;
    private UserShortDto user;
    private CommunityShortDto community;
    private LocalDateTime dateOfCreation;
}
