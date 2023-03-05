package com.evoter.user.dto;

import com.evoter.role.model.Role;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegistrationResponseDTO {

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private String email;

    @NotNull
    private String phoneNumber;

    private Role role;
}
