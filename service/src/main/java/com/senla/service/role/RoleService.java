package com.senla.service.role;

import com.senla.entity.Role;

import java.util.List;

public interface RoleService {
    Role addRole(Role role);

    Role getRoleByName(String name);

    Role updateRole(Role role);

    List<Role> getAllRoles();

}
