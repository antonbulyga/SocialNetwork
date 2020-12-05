package com.senla.converters;

import com.senla.dto.RoleShortDto;
import com.senla.entity.Role;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RoleToRoleShortDto implements Converter<Role, RoleShortDto> {

    @Override
    public RoleShortDto convert(Role role) {
        return RoleShortDto.builder()
                .id(role.getId())
                .name(role.getName())
                .build();
    }
}
