package com.evoter.user.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateUpdateUserDTO {

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private String email;

    @NotNull
    private String phoneNumber;

    private String publicKey;

    private String privateKey;

    @NotNull
    private Long roleId;
}
