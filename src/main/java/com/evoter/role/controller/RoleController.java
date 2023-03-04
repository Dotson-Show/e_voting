package com.evoter.role.controller;

import com.evoter.general.dto.Response;
import com.evoter.general.enums.ResponseCodeAndMessage;
import com.evoter.general.service.GeneralService;
import com.evoter.role.dto.CreateUpdateRoleDTO;
import com.evoter.role.dto.RoleDTO;
import com.evoter.role.dto.RoleListDTO;
import com.evoter.role.dto.RoleRequestDTO;
import com.evoter.role.service.RoleService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/adminRoles")
public class RoleController {

    private final HttpServletRequest request;
    private final GeneralService generalService;
    private final RoleService adminRoleService;

    public RoleController(HttpServletRequest request, GeneralService generalService, RoleService adminRoleService) {
        this.request = request;
        this.generalService = generalService;
        this.adminRoleService = adminRoleService;
    }

    //    @PreAuthorize("hasAuthority('CREATE_ROLE')")
    @PostMapping("/create")
    public Response createRole(@RequestBody CreateUpdateRoleDTO requestDTO) {

        RoleDTO data = adminRoleService.addRole(requestDTO);
        return generalService.prepareResponse(ResponseCodeAndMessage.SUCCESSFUL_0, data);
    }

    //    @PreAuthorize("hasAuthority('CREATE_ROLE')")
    @PostMapping("/update/{roleId}")
    public Response updateRole(@RequestBody CreateUpdateRoleDTO requestDTO, @PathVariable Long roleId, Principal principal) {

        RoleDTO data = adminRoleService.updateRole(requestDTO, roleId);
        return generalService.prepareResponse(ResponseCodeAndMessage.SUCCESSFUL_0, data);
    }

    //    @PreAuthorize("hasAuthority('DELETE_ROLE')")
    @PostMapping("/delete/{id}")
    public Response deleteRole(@PathVariable Long id) {

        adminRoleService.deleteRole(id);
        return generalService.prepareResponse(ResponseCodeAndMessage.SUCCESSFUL_0, null);
    }

    //    @PreAuthorize("hasAuthority('VIEW_ROLE')")
    @PostMapping()
    public Response getAllRoles(@RequestBody RoleRequestDTO requestDTO) {

        RoleListDTO data = adminRoleService.getAllRoles(requestDTO);
        return generalService.prepareResponse(ResponseCodeAndMessage.SUCCESSFUL_0, data);
    }

}
