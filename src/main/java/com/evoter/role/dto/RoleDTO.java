package com.evoter.role.dto;

import com.evoter.permission.dto.PermissionDTO;
import lombok.Data;

import java.util.List;

@Data
public class RoleDTO {
    private Long id;

    private String name;

    private List<PermissionDTO> permissions;

}