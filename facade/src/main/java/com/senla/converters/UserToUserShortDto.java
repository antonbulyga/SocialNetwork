package com.senla.converters;

import com.senla.dto.UserShortDto;
import com.senla.entity.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserToUserShortDto implements Converter<User, UserShortDto> {
    @Override
    public UserShortDto convert(User user) {
        return UserShortDto.builder()
                .id(user.getId())
                /*.email(user.getUserName())
                .userName(user.getUserName())
                .password(user.getPassword())
                .creationTime(user.getCreationTime())*/
                .build();
    }
}
