//package com.evoter.user.service.implementation;
//
//import com.evoter.general.service.GeneralService;
//import com.evoter.permission.dto.PermissionDTO;
//import com.evoter.role.dto.RoleDTO;
//import com.evoter.role.service.RoleService;
//import com.evoter.user.dto.UserDTO;
//import com.evoter.user.model.User;
//import com.evoter.user.service.UserService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Slf4j
//@Service
//public class CustomUserDetailsService implements UserDetailsService {
//
//    private final RoleService adminRoleService;
//
//    private final UserService userService;
//
//    public final GeneralService generalService;
//
//    public CustomUserDetailsService(RoleService adminRoleService, UserService userService, GeneralService generalService) {
//        this.adminRoleService = adminRoleService;
//        this.userService = userService;
//        this.generalService = generalService;
//    }
//
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//
//        UserDTO userAuthDTO = validateAdminUser(username);
//
//        List<GrantedAuthority> grantedAuthorities = getGrantedAuthorities(userAuthDTO.getRole().getPermissions());
//        return new org.springframework.security.core.userdetails.User(userAuthDTO.getEmail(), "", grantedAuthorities);
//    }
//
//    private List<GrantedAuthority> getGrantedAuthorities(List<PermissionDTO> permissionDTOList) {
//        return permissionDTOList.stream().map(permissionDTO -> new SimpleGrantedAuthority(permissionDTO.getName())).collect(Collectors.toList());
//    }
//
//    public UserDTO validateAdminUser(String email) {
//
//        User user = userService.getUserForLogin(email);
//
//        return getAdminUserDTO(user);
//    }
//
//    public UserDTO getAdminUserDTO(User adminUser) {
//        log.info("Converting Admin User to Admin User DTO");
//
//        UserDTO adminUserDTO = new UserDTO();
//        generalService.createDTOFromModel(adminUser, adminUserDTO);
//
//        //get role dto
//        RoleDTO roleDTO = adminRoleService.getAdminRoleDTO(adminUser.getRole());
//        adminUserDTO.setRole(roleDTO);
//
//        return adminUserDTO;
//    }
//}