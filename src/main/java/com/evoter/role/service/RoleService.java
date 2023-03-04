package com.evoter.role.service;

import com.evoter.permission.model.Permission;
import com.evoter.role.dto.CreateUpdateRoleDTO;
import com.evoter.role.dto.RoleDTO;
import com.evoter.role.dto.RoleListDTO;
import com.evoter.role.dto.RoleRequestDTO;
import com.evoter.role.model.Role;

import java.util.List;

public interface RoleService {

    RoleListDTO getAllRoles(RoleRequestDTO requestDTO);

    Role getRoleByName(String name);

    RoleDTO addRole(CreateUpdateRoleDTO roleDTO);

    Role addRole(Role role, String name, List<Permission> permissions);

    RoleDTO updateRole(CreateUpdateRoleDTO roleDTO, Long id);

    RoleDTO getOneRole(Long roleId);

    void deleteRole(Long roleId);

    void deleteRoleByName(String roleName);

    RoleDTO getAdminRoleDTO(Role adminRole);
}
