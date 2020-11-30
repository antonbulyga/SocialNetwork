package com.senla.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunityDto {
    @NotNull(message = "Id of the community is mandatory")
    private Long id;

    private List<UserShortDto> users;

    @NotBlank(message = "Name of the community is mandatory")
    private String name;

    @NotNull(message = "Admin user can not be null")
    private UserShortDto adminUser;

    private List<PostShortDto> posts;

    public CommunityDto(@NotNull(message = "Id of the community is mandatory") Long id, @NotBlank(message = "Name of the community is mandatory") String name,
                        @NotNull(message = "Admin user can not be null") UserShortDto adminUser) {
        this.id = id;
        this.name = name;
        this.adminUser = adminUser;
    }
}
