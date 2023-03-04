package com.evoter.user.dto;

import com.evoter.role.dto.RoleDTO;
import lombok.Data;

@Data
public class UserDTO {
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private RoleDTO role;

    private String token;

}
