package com.senla.service;

import com.senla.entity.Role;

import java.util.List;

public interface RoleService {
    Role addRole(Role role);
    void deleteRole(long id);
    Role getRoleByName(String name);
    Role editRole(Role role);
    List<Role> getAllRoles();
}
