package com.senla.dto.friendship;

import com.senla.dto.user.UserNestedDto;
import com.senla.enumeration.FriendshipStatus;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FriendshipDto {
    @NotNull(message = "Friendship status is mandatory")
    private FriendshipStatus friendshipStatus;

    private UserNestedDto userOne;

    private UserNestedDto userTwo;

    private UserNestedDto actionUser;

}
