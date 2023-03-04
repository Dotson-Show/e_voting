package com.evoter.role.dto;

import com.evoter.general.dto.PageableResponseDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class RoleListDTO extends PageableResponseDTO {

    @JsonProperty("roles")
    private List<RoleDTO> roleDTOList;
}
