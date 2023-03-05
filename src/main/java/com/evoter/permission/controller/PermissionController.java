package com.evoter.permission.controller;

import com.evoter.general.dto.Response;
import com.evoter.general.enums.ResponseCodeAndMessage;
import com.evoter.general.service.GeneralService;
import com.evoter.permission.dto.PermissionListDTO;
import com.evoter.permission.dto.PermissionRequestDTO;
import com.evoter.permission.service.PermissionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/permissions")
public class PermissionController {

    private final GeneralService generalService;
    private final PermissionService permissionService;

    public PermissionController(GeneralService generalService, PermissionService permissionService) {
        this.generalService = generalService;
        this.permissionService = permissionService;
    }

    //    @PreAuthorize("hasAuthority('VIEW_PERMISSION')")
    @PostMapping()
    public Response getAllPermission(@RequestBody PermissionRequestDTO requestDTO) {
        PermissionListDTO data = permissionService.getAllPermission(requestDTO);
        return generalService.prepareResponse(ResponseCodeAndMessage.SUCCESSFUL_0, data);
    }

}
