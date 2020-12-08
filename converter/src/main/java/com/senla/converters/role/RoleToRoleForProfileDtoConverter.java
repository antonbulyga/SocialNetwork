package com.senla.converters.role;

import com.senla.dto.role.RoleForProfileDto;
import com.senla.entity.Role;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RoleToRoleForProfileDtoConverter implements Converter<Role, RoleForProfileDto> {

    @Override
    public RoleForProfileDto convert(Role role) {
        return RoleForProfileDto.builder()
                .id(role.getId())
                .name(role.getName())
                .build();
    }
}
