package com.senla.dto.user;

import com.senla.dto.community.CommunityForUserDto;
import com.senla.dto.dialog.DialogForMessageAndUserDto;
import com.senla.dto.like.LikeForPostAndUserDto;
import com.senla.dto.message.MessageForListDto;
import com.senla.dto.post.PostForUserAndCommunityDto;
import com.senla.dto.profile.ProfileForUserDto;
import com.senla.dto.role.RoleForProfileDto;
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

    private ProfileForUserDto profile;

    private List<CommunityForUserDto> communities;

    private List<RoleForProfileDto> roles;

    private List<PostForUserAndCommunityDto> posts;

    private List<MessageForListDto> messages;

    private List<LikeForPostAndUserDto> likes;

    private LocalDateTime creationTime;

    private CommunityForUserDto community;

    private List<DialogForMessageAndUserDto> dialogs;
}
