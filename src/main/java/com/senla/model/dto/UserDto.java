package com.senla.model.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String email;
    private String userName;
    private String password;
    private ProfileShortDto profile;
    private List<CommunityShortDto> communities;
    private List<RoleShortDto> roles;
    private List<PostShortDto> posts;
    private List<MessageShortDto> messages;
    private List<LikeShortDto> likes;
    private LocalDateTime creationTime;
    private CommunityShortDto community;
    private List<DialogShortDto> dialogs;
}
