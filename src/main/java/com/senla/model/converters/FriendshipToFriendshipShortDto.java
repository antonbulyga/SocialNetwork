package com.senla.model.converters;

import com.senla.model.dto.FriendshipShortDto;
import com.senla.model.entity.Friendship;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class FriendshipToFriendshipShortDto implements Converter<Friendship, FriendshipShortDto> {
    @Override
    public FriendshipShortDto convert(Friendship friendship) {
        return FriendshipShortDto.builder()
                .id(friendship.getId())
                .friendshipStatus(friendship.getFriendshipStatus())
                .build();
    }
}
