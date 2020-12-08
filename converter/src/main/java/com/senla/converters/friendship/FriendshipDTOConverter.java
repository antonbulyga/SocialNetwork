package com.senla.converters.friendship;

import com.senla.converters.user.UserToUserNestedDtoConverter;
import com.senla.dto.friendship.FriendshipDto;
import com.senla.entity.Friendship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class FriendshipDTOConverter implements Converter<Friendship, FriendshipDto> {

    private UserToUserNestedDtoConverter userToUserNestedDtoConverter;

    @Autowired
    public FriendshipDTOConverter(UserToUserNestedDtoConverter userToUserNestedDtoConverter) {
        this.userToUserNestedDtoConverter = userToUserNestedDtoConverter;
    }

    @Override
    public FriendshipDto convert(Friendship friendship) {
        return FriendshipDto.builder()
                .friendshipStatus(friendship.getFriendshipStatus())
                .actionUser(userToUserNestedDtoConverter.convert(friendship.getActionUser()))
                .userOne(userToUserNestedDtoConverter.convert(friendship.getUserOne()))
                .userTwo(userToUserNestedDtoConverter.convert(friendship.getUserTwo()))
                .build();

    }
}
