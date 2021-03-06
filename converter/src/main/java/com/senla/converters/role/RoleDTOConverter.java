package com.senla.converters.role;

import com.senla.converters.user.UserToUserNestedDtoConverter;
import com.senla.dto.role.RoleDto;
import com.senla.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class RoleDTOConverter implements Converter<Role, RoleDto> {

    private final UserToUserNestedDtoConverter userToUserNestedDtoConverter;

    @Autowired
    public RoleDTOConverter(UserToUserNestedDtoConverter userToUserNestedDtoConverter) {
        this.userToUserNestedDtoConverter = userToUserNestedDtoConverter;
    }

    @Override
    public RoleDto convert(Role role) {
        return RoleDto.builder()
                .id(role.getId())
                .name(role.getName())
                .users(role.getUsers().stream().map(userToUserNestedDtoConverter::convert).collect(Collectors.toList()))
                .build();
    }
}
