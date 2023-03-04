package com.evoter.user.dto;

import com.evoter.general.dto.PageableResponseDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserListDTO extends PageableResponseDTO {

    @JsonProperty("users")
    private List<UserDTO> userDTOList;
}
