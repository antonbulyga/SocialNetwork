package com.senla.model.dto;

import com.senla.model.enumeration.FriendshipStatus;
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
