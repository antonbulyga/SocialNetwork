package com.senla.model.dto;

import com.senla.model.enumeration.FriendshipStatus;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FriendshipDto {
    private FriendshipStatus friendshipStatus;
    private UserShortDto userOne;
    private UserShortDto userTwo;
    private UserShortDto actionUser;

}
