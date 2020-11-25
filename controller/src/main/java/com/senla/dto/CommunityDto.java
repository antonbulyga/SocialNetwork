package com.senla.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunityDto {
    private Long id;
    private List<UserShortDto> users;
    private String name;
    private UserShortDto adminUser;
    private List<PostShortDto> posts;

}
