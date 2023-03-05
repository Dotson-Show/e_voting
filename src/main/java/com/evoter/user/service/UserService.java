package com.evoter.user.service;

import com.evoter.user.dto.*;
import com.evoter.user.model.User;

import java.lang.reflect.InvocationTargetException;

public interface UserService {

    UserDTO getOneAdminUser(String email);

    User getUserForLogin(String email);

    void logoutUser(String email);

    UserDTO registration(RegistrationRequestDTO createUserDto);

    UserDTO addUser(CreateUpdateUserDTO createAdminUserDto);

    UserDTO updateUser(CreateUpdateUserDTO createAdminUserDto, Long userId);

    void validateThatAdminUserDoesNotExist(String email);

    UserListDTO getAllUsers(UserRequestDTO requestDTO);

//    boolean validateAdminUserExistAsAdminAndEnabled(String email);

    UserDTO getUserDTO(User adminUser) throws InvocationTargetException, IllegalAccessException;

    UserListDTO getAllUsersByPermissionName(String permissionName);

    UserSettingsDTO getUserSettings(String email);
}

