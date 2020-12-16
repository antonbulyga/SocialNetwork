package com.senla.dto.community;

import com.senla.dto.post.PostForUserAndCommunityDto;
import com.senla.dto.user.UserNestedDto;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunityDto {
    @NotNull(message = "Id of the community is mandatory")
    private long id;

    private List<UserNestedDto> users;

    @NotBlank(message = "Name of the community is mandatory")
    private String name;

    @NotNull(message = "Admin user can not be null")
    private UserNestedDto adminUser;

    private List<PostForUserAndCommunityDto> posts;

}
