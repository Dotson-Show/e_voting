package com.evoter.user.dto;

import com.evoter.role.dto.RoleDTO;
import lombok.Data;

@Data
public class UserAuthDTO {

    private String email;

    private boolean enabled = false;

    private RoleDTO role;
}
