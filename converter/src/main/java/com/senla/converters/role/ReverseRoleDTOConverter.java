package com.senla.converters.role;

import com.senla.dto.role.RoleDto;
import com.senla.entity.Role;
import com.senla.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ReverseRoleDTOConverter implements Converter<RoleDto, Role> {

    private final UserService userService;

    @Autowired
    public ReverseRoleDTOConverter(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Role convert(RoleDto roleDto) {
        return Role.builder()
                .id(roleDto.getId())
                .name(roleDto.getName())
                .users(roleDto.getUsers().stream().map(u -> userService.getUserById(u.getId())).collect(Collectors.toList()))
                .build();
    }
}
