package com.senla.dto;

import com.senla.enumeration.FriendshipStatus;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FriendshipDto {
    @NotNull(message = "Id of the friendship is mandatory")
    private Long id;

    @NotNull(message = "Friendship status is mandatory")
    private FriendshipStatus friendshipStatus;

    private UserShortDto userOne;

    private UserShortDto userTwo;

    private UserShortDto actionUser;

}
