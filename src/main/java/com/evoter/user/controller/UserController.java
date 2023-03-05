package com.evoter.user.controller;

import com.evoter.general.dto.Response;
import com.evoter.general.enums.ResponseCodeAndMessage;
import com.evoter.general.service.GeneralService;
import com.evoter.user.dto.*;
import com.evoter.user.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final GeneralService generalService;
    private final UserService userService;

    public UserController(GeneralService generalService, UserService adminUserService) {
        this.generalService = generalService;
        this.userService = adminUserService;
    }

    @PostMapping("/registration")
    public Response registration(@RequestBody RegistrationRequestDTO requestDTO) {

        UserDTO data = userService.registration(requestDTO);
        return generalService.prepareResponse(ResponseCodeAndMessage.SUCCESSFUL_0, data);
    }

    //    @PreAuthorize("hasAuthority('CREATE_USER')")
    @PostMapping("/createAdminUser")
    public Response createUser(@RequestBody CreateUpdateUserDTO requestDTO) {

        UserDTO data = userService.addUser(requestDTO);
        return generalService.prepareResponse(ResponseCodeAndMessage.SUCCESSFUL_0, data);
    }

    //    @PreAuthorize("hasAuthority('CREATE_USER')")
    @PostMapping("/update/{userId}")
    public Response updateUser(@RequestBody CreateUpdateUserDTO requestDTO, @PathVariable Long userId) {

        UserDTO data = userService.updateUser(requestDTO, userId);
        return generalService.prepareResponse(ResponseCodeAndMessage.SUCCESSFUL_0, data);
    }

    //    @PreAuthorize("hasAuthority('VIEW_USER')")
    @PostMapping()
    public Response getAll(@RequestBody UserRequestDTO requestDTO) {

        UserListDTO data = userService.getAllUsers(requestDTO);
        return generalService.prepareResponse(ResponseCodeAndMessage.SUCCESSFUL_0, data);
    }


    @PostMapping("/getLoggedInUserInfo")
    public Response getLoggedInUser(Principal principal) {
        UserDTO data = userService.getOneAdminUser(principal.getName());
        return generalService.prepareResponse(ResponseCodeAndMessage.SUCCESSFUL_0, data);
    }

}
