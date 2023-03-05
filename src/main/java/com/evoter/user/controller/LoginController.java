//package com.evoter.user.controller;
//
//import com.evoter.general.dto.Response;
//import com.evoter.general.enums.ResponseCodeAndMessage;
//import com.evoter.general.service.GeneralService;
//import com.evoter.user.dto.LoginRequestDTO;
//import com.evoter.user.dto.UserDTO;
//import com.evoter.user.service.LoginService;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/api/v1")
//public class LoginController {
//
//    private final LoginService loginService;
//    private final GeneralService generalService;
//
//    public LoginController(LoginService loginService, GeneralService generalService) {
//        this.loginService = loginService;
//        this.generalService = generalService;
//    }
//
//    @PostMapping("/auth/login")
//    public Response getToken(@RequestBody LoginRequestDTO requestDTO) {
//        UserDTO data = loginService.authenticateUser(requestDTO);
//        return generalService.prepareResponse(ResponseCodeAndMessage.SUCCESSFUL_0, data);
//    }
//
//}
