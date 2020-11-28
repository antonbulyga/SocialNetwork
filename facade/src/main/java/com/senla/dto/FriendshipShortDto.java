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
public class FriendshipShortDto {
    @NotNull(message = "Id of the friendship is mandatory")
    private Long id;

    @NotNull(message = "Friendship status is mandatory")
    private FriendshipStatus friendshipStatus;

}
