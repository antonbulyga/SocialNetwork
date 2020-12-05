package com.senla.service.role;

import com.senla.entity.Role;

import java.util.List;

public interface RoleService {
    Role addRole(Role role);

    void deleteRole(long id);

    Role getRoleByName(String name);

    Role updateRole(Role role);

    List<Role> getAllRoles();

    Role getRole(Long id);
}
