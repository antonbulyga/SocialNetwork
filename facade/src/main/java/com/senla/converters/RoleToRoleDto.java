package com.senla.converters;

import com.senla.dto.RoleDto;
import com.senla.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class RoleToRoleDto implements Converter<Role, RoleDto> {
    private UserToUserShortDto userToUserShortDto;

    @Autowired
    public RoleToRoleDto(UserToUserShortDto userToUserShortDto) {
        this.userToUserShortDto = userToUserShortDto;
    }

    @Override
    public RoleDto convert(Role role) {
        return RoleDto.builder()
                .id(role.getId())
                .name(role.getName())
                .users(role.getUsers().stream().map(u -> userToUserShortDto.convert(u)).collect(Collectors.toList()))
                .build();
    }
}
