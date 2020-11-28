package com.senla.facade;

import com.senla.converters.RoleDtoToRole;
import com.senla.converters.RoleToRoleDto;
import com.senla.dto.RoleDto;
import com.senla.entity.Role;
import com.senla.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RoleFacade {

    private final RoleService roleService;
    private final RoleToRoleDto roleToRoleDto;
    private final RoleDtoToRole roleDtoToRole;

    @Autowired
    public RoleFacade(RoleService roleService, RoleToRoleDto roleToRoleDto, RoleDtoToRole roleDtoToRole) {
        this.roleService = roleService;
        this.roleToRoleDto = roleToRoleDto;
        this.roleDtoToRole = roleDtoToRole;
    }

    public RoleDto addRole(RoleDto roleDto) {
        roleService.addRole(roleDtoToRole.convert(roleDto));
        return roleDto;
    }

    public void deleteRole(long id) {
        roleService.deleteRole(id);
    }

    public RoleDto updateRole(RoleDto roleDto) {
        Role role = roleDtoToRole.convert(roleDto);
        roleService.updateRole(role);
        return roleDto;
    }

    public List<RoleDto> getAllRoles() {
        List<Role> roles = roleService.getAllRoles();
        return roles.stream().map(roleToRoleDto::convert).collect(Collectors.toList());
    }

    public RoleDto getRoleDto(Long id) {
        return roleToRoleDto.convert(roleService.getRole(id));
    }

    public Role getRole(Long id) {
        return roleService.getRole(id);
    }

    public RoleDto getRoleByName(String name) {
        Role role = roleService.getRoleByName(name);
        return roleToRoleDto.convert(role);
    }
}
