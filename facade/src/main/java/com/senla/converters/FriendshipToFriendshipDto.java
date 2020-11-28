package com.senla.converters;

import com.senla.dto.FriendshipDto;
import com.senla.entity.Friendship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class FriendshipToFriendshipDto implements Converter<Friendship, FriendshipDto> {

    private UserToUserShortDto userToUserShortDto;

    @Autowired
    public FriendshipToFriendshipDto(UserToUserShortDto userToUserShortDto) {
        this.userToUserShortDto = userToUserShortDto;
    }

    @Override
    public FriendshipDto convert(Friendship friendship) {
        return FriendshipDto.builder()
                .id(friendship.getId())
                .friendshipStatus(friendship.getFriendshipStatus())
                .actionUser(userToUserShortDto.convert(friendship.getActionUser()))
                .userOne(userToUserShortDto.convert(friendship.getUserOne()))
                .userTwo(userToUserShortDto.convert(friendship.getUserTwo()))
                .build();

    }
}
