package com.evoter.user.service;

import com.evoter.user.dto.LoginRequestDTO;
import com.evoter.user.dto.UserDTO;

public interface LoginService {
    UserDTO authenticateUser(LoginRequestDTO requestDTO);
}
