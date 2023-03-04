package com.evoter.permission.dto;

import com.evoter.general.dto.PageableResponseDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class PermissionListDTO extends PageableResponseDTO {

    @JsonProperty("permissions")
    private List<PermissionDTO> permissionDTOList;
}
