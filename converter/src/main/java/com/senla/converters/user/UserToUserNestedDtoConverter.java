package com.senla.converters.user;

import com.senla.dto.user.UserNestedDto;
import com.senla.entity.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserToUserNestedDtoConverter implements Converter<User, UserNestedDto> {

    @Override
    public UserNestedDto convert(User user) {
        return UserNestedDto.builder()
                .id(user.getId())
                .userName(user.getUserName())
                .build();
    }
}
