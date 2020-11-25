package com.senla.dto;

import com.senla.enumeration.FriendshipStatus;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FriendshipShortDto {
    private Long id;
    private FriendshipStatus friendshipStatus;

}
