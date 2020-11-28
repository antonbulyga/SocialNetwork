package com.senla.converters;

import com.senla.dto.RoleDto;
import com.senla.entity.Role;
import com.senla.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class RoleDtoToRole implements Converter<RoleDto, Role> {

    private UserService userService;

    @Autowired
    public RoleDtoToRole(UserService userService) {
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
