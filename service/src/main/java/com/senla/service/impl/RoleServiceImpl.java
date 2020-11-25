package com.senla.service.impl;

import com.senla.entity.Role;
import com.senla.repository.RoleRepository;
import com.senla.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service
@Transactional
@Slf4j
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role addRole(Role role) {
        roleRepository.save(role);
        log.info("Adding role");
        return role;
    }

    @Override
    public void deleteRole(long id) {
        roleRepository.deleteById(id);
    }

    @Override
    public Role getRoleByName(String name) {
        Role role = roleRepository.getRoleByName(name);
        log.info("Getting role by name");
        return role;
    }

    @Override
    public Role editRole(Role role) {
        roleRepository.save(role);
        log.info("Updating role");
        return role;
    }

    @Override
    public List<Role> getAllRoles() {
        List<Role> roles = roleRepository.findAll();
        log.info("Getting all roles");
        return roles;
    }
}
