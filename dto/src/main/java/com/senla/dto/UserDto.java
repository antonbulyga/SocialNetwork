package com.senla.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    @NotNull(message = "Field id is mandatory")
    private long id;

    @NotBlank(message = "Email is mandatory")
    private String email;

    @NotBlank(message = "User name is mandatory")
    private String userName;

    @NotBlank(message = "Password is mandatory")
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
