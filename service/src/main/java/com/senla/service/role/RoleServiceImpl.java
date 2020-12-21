package com.senla.service.role;

import com.senla.entity.Role;
import com.senla.exception.EntityNotFoundException;
import com.senla.repository.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@Slf4j
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role addRole(Role role) {
        log.info("Adding role");
        roleRepository.save(role);
        return role;
    }

    @Override
    public Role getRoleByName(String name) {
        Role role = roleRepository.getRoleByName(name);
        log.info("Getting role by name");
        if (role == null) {
            log.warn("No role found with this name");
            throw new EntityNotFoundException("No role found with this name");
        }
        return role;
    }

    @Override
    public Role updateRole(Role role) {
        log.info("Updating role");
        roleRepository.save(role);
        return role;
    }

    @Override
    public List<Role> getAllRoles() {
        log.info("Getting all roles");
        List<Role> roles = roleRepository.findAll();
        if (roles.isEmpty()) {
            log.warn("Empty role list");
            throw new EntityNotFoundException("Empty role list");
        }
        return roles;
    }
}
