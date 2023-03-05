package com.evoter.permission.service;

import com.evoter.permission.dto.PermissionDTO;
import com.evoter.permission.dto.PermissionListDTO;
import com.evoter.permission.dto.PermissionRequestDTO;
import com.evoter.permission.enums.PermissionType;
import com.evoter.permission.model.Permission;

import java.util.List;

public interface PermissionService {
    PermissionListDTO getAllAdminPermission(PermissionRequestDTO requestDTO);

    PermissionListDTO getAllPermission(PermissionRequestDTO requestDTO);

    PermissionListDTO getAllUserPermission(PermissionRequestDTO requestDTO);

    Permission createPermission(String name, PermissionType permissionType);

    Permission findByName(String name);

    PermissionDTO getPermissionDTO(Permission permission);

    List<Permission> getPermissionsByPermissionType(PermissionType permissionType);

    void createPermissionsIfNecessary();

}
