package com.evoter.role.service.implementation;

import com.evoter.exception.GeneralException;
import com.evoter.general.enums.ResponseCodeAndMessage;
import com.evoter.general.service.GeneralService;
import com.evoter.permission.dto.PermissionDTO;
import com.evoter.permission.model.Permission;
import com.evoter.permission.service.PermissionService;
import com.evoter.role.dto.CreateUpdateRoleDTO;
import com.evoter.role.dto.RoleDTO;
import com.evoter.role.dto.RoleListDTO;
import com.evoter.role.dto.RoleRequestDTO;
import com.evoter.role.model.Role;
import com.evoter.role.repository.RoleRepository;
import com.evoter.role.service.RoleService;
import com.evoter.user.repository.UserRepository;
import com.evoter.util.GeneralUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RoleServiceImpl implements RoleService {

    private final GeneralService generalService;
    private final RoleRepository adminRoleRepository;
    private final UserRepository adminUserRepository;
    private final PermissionService permissionService;

    public RoleServiceImpl(GeneralService generalService, RoleRepository adminRoleRepository, UserRepository adminUserRepository, PermissionService permissionService) {
        this.generalService = generalService;
        this.adminRoleRepository = adminRoleRepository;
        this.adminUserRepository = adminUserRepository;
        this.permissionService = permissionService;
    }

    @Override
    public RoleListDTO getAllRoles(RoleRequestDTO requestDTO) {
        log.info("Getting Admin Roles List with payload = {}", requestDTO);

        Pageable paged = generalService.getPageableObject(requestDTO.getSize(), requestDTO.getPage(), Sort.by(Sort.Direction.DESC, "lastModifiedDate"));

        Page<Role> adminRolePage = adminRoleRepository.findAll(paged);

        return getAdminRoleListDTO(adminRolePage);
    }

    @Override
    public Role getRoleByName(String name) {
        log.info("Getting Admin Role using name => {}", name);

        return adminRoleRepository.findByName(name);
    }

    @Override
    public RoleDTO addRole(CreateUpdateRoleDTO requestDTO) {
        log.info("Request to create a Role with payload = {}", requestDTO);

        //check if role name is valid
        if (GeneralUtil.stringIsNullOrEmpty(requestDTO.getName())) {
            throw new GeneralException(ResponseCodeAndMessage.INCOMPLETE_PARAMETERS_91.responseCode, "Role name cannot be empty or null");
        }

        //verify role name
        validateRoleName(requestDTO.getName());

        //permission
        List<Permission> permissions = requestDTO.getPermissionNames().stream().map(permissionService::findByName).collect(Collectors.toList());


        Role role = new Role();

        // save role
        role = addRole(role, requestDTO.getName(), permissions);


        // get DTO
        return getAdminRoleDTO(role);
    }

    @Override
    public Role addRole(Role role, String name, List<Permission> permissions) {
        role.setName(name);
        role.setPermissions(permissions);

        return adminRoleRepository.save(role);
    }

    @Override
    public RoleDTO updateRole(CreateUpdateRoleDTO requestDTO, Long id) {
        log.info("Request to edit a role with id = {} with payload = {}", id, requestDTO);

        // get role by id
        Role role = getRoleById(id);

        // check that the new role name is not empty
        if (GeneralUtil.stringIsNullOrEmpty(requestDTO.getName())) {
            throw new GeneralException(ResponseCodeAndMessage.INCOMPLETE_PARAMETERS_91.responseCode, "Role name cannot be null or empty!");
        } else if (!requestDTO.getName().equals(role.getName())) {
            validateRoleName(requestDTO.getName());
        }

        //permission
        List<Permission> permissions = requestDTO.getPermissionNames().stream().map(permissionService::findByName).collect(Collectors.toList());

        // set values
        role = addRole(role, requestDTO.getName(), permissions);


        // get DTO
        return getAdminRoleDTO(role);
    }

    @Override
    public RoleDTO getOneRole(Long roleId) {
        log.info("Getting Admin Role using roleId => {}", roleId);

        Role adminRole = getRoleById(roleId);

        return getAdminRoleDTO(adminRole);
    }

    @Override
    public void deleteRole(Long roleId) {
        log.info("Deleting an admin role with id = {}", roleId);

        // get role
        Optional<Role> validRole = adminRoleRepository.findById(roleId);

        if (!validRole.isPresent()) {
            throw new GeneralException(ResponseCodeAndMessage.RECORD_NOT_FOUND_88.responseCode, "Admin Role does not exist");
        }

        // check if the role is not mapped to an admin user
        boolean roleMappedToAdminUser = adminUserRepository.existsByRole_Id(roleId);
        if (roleMappedToAdminUser) {
            throw new GeneralException(ResponseCodeAndMessage.OPERATION_NOT_SUPPORTED_93.responseCode, "Role mapped to a user cannot be deleted");
        }

        adminRoleRepository.delete(validRole.get());
    }

    @Override
    public void deleteRoleByName(String roleName) {
        log.info("Deleting an admin role with roleNam = {}", roleName);

        // get role
        Role validRole = adminRoleRepository.findByName(roleName);

        if (Objects.isNull(validRole)) {
            throw new GeneralException(ResponseCodeAndMessage.RECORD_NOT_FOUND_88.responseCode, "Admin Role does not exist");
        }

        // check if the role is not mapped to an admin user
        boolean roleMappedToAdminUser = adminUserRepository.existsByRole_Name(roleName);
        if (roleMappedToAdminUser) {
            throw new GeneralException(ResponseCodeAndMessage.OPERATION_NOT_SUPPORTED_93.responseCode, "Role mapped to a user cannot be deleted");
        }


        adminRoleRepository.delete(validRole);
    }

    @Override
    public RoleDTO getAdminRoleDTO(Role adminRole) {

        RoleDTO adminRoleDTO = new RoleDTO();
        generalService.createDTOFromModel(adminRole, adminRoleDTO);

        List<PermissionDTO> permissionDTOList = adminRole.getPermissions().stream().map(permissionService::getPermissionDTO).collect(Collectors.toList());

        adminRoleDTO.setPermissions(permissionDTOList);

        return adminRoleDTO;
    }

    private Role getRoleById(Long roleId) {
        return adminRoleRepository.findById(roleId).orElseThrow(() -> new GeneralException(ResponseCodeAndMessage.RECORD_NOT_FOUND_88));
    }

    private void validateRoleName(String name) {
        boolean exists = adminRoleRepository.existsByName(name);
        if (exists) {
            throw new GeneralException(ResponseCodeAndMessage.ALREADY_EXIST_86.responseCode, "Role " + name + " already exist");
        }
    }

    private RoleListDTO getAdminRoleListDTO(Page<Role> adminRolePage) {
        RoleListDTO listDTO = new RoleListDTO();

        List<Role> roleList = adminRolePage.getContent();
        if (adminRolePage.getContent().size() > 0) {
            listDTO.setHasNextRecord(adminRolePage.hasNext());
            listDTO.setTotalCount((int) adminRolePage.getTotalElements());
        }

        List<RoleDTO> roleDTOS = convertToAdminRoleDTOList(roleList);
        listDTO.setRoleDTOList(roleDTOS);

        return listDTO;
    }


    private List<RoleDTO> convertToAdminRoleDTOList(List<Role> roleList) {
        log.info("Converting Role List to Role DTO List");

        return roleList.stream().map(this::getAdminRoleDTO).collect(Collectors.toList());
    }
}
