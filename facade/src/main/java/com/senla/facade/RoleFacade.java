package com.senla.facade;

import com.senla.converters.role.ReverseRoleDTOConverter;
import com.senla.converters.role.RoleDTOConverter;
import com.senla.dto.role.RoleDto;
import com.senla.entity.Role;
import com.senla.service.role.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RoleFacade {

    private final RoleService roleService;
    private final RoleDTOConverter roleDTOConverter;
    private final ReverseRoleDTOConverter reverseRoleDTOConverter;

    @Autowired
    public RoleFacade(RoleService roleService, RoleDTOConverter roleDTOConverter, ReverseRoleDTOConverter reverseRoleDTOConverter) {
        this.roleService = roleService;
        this.roleDTOConverter = roleDTOConverter;
        this.reverseRoleDTOConverter = reverseRoleDTOConverter;
    }

    public RoleDto addRole(RoleDto roleDto) {
        roleService.addRole(reverseRoleDTOConverter.convert(roleDto));
        return roleDto;
    }

    public void deleteRole(long id) {
        roleService.deleteRole(id);
    }

    public RoleDto updateRole(RoleDto roleDto) {
        Role role = reverseRoleDTOConverter.convert(roleDto);
        roleService.updateRole(role);
        return roleDto;
    }

    public List<RoleDto> getAllRoles() {
        List<Role> roles = roleService.getAllRoles();
        return roles.stream().map(roleDTOConverter::convert).collect(Collectors.toList());
    }

    public RoleDto getRoleByName(String name) {
        Role role = roleService.getRoleByName(name);
        return roleDTOConverter.convert(role);
    }
}
