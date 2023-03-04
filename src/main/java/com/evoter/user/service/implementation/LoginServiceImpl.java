//package com.evoter.user.service.implementation;
//
//import com.evoter.exception.GeneralException;
//import com.evoter.general.enums.ResponseCodeAndMessage;
//import com.evoter.security.jwt.JwtUtil;
//import com.evoter.user.dto.LoginRequestDTO;
//import com.evoter.user.dto.UserDTO;
//import com.evoter.user.service.LoginService;
//import com.evoter.user.service.UserService;
//import com.evoter.util.GeneralUtil;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//
//import java.util.Objects;
//
//@Slf4j
//@Service
//public class LoginServiceImpl implements LoginService {
//
//    private final JwtUtil jwtUtil;
//    private final UserService userService;
//
//    public LoginServiceImpl(JwtUtil jwtUtil, UserService userService) {
//        this.jwtUtil = jwtUtil;
//        this.userService = userService;
//    }
//
//    @Override
//    public UserDTO authenticateUser(LoginRequestDTO requestDTO) {
//        log.info("Sending Request to login with email =>{}", requestDTO.getEmail());
//
//        if (!GeneralUtil.stringIsNullOrEmpty(requestDTO.getEmail())) {
//            UserDTO userDTO;
//            String email = requestDTO.getEmail();
//
//            userDTO = userService.getOneAdminUser(email);
//
//            if (Objects.nonNull(userDTO)) {
//                String generatedToken = jwtUtil.generateToken(email, requestDTO.getPassword());
//                userDTO.setToken(generatedToken);
//                return userDTO;
//
//            } else {
//                throw new GeneralException(ResponseCodeAndMessage.AUTHENTICATION_FAILED_95.responseCode, "Invalid email");
//            }
//        }
//        throw new GeneralException(ResponseCodeAndMessage.AUTHENTICATION_FAILED_95.responseCode, "Error occurred during SSO validation");
//    }
//}
