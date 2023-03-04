package com.evoter.startup;

import com.evoter.permission.enums.PermissionType;
import com.evoter.permission.model.Permission;
import com.evoter.permission.service.PermissionService;
import com.evoter.role.model.Role;
import com.evoter.role.service.RoleService;
import com.evoter.user.constants.SuperAdminUserConstant;
import com.evoter.user.model.User;
import com.evoter.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    @Value("${super.admin.emails}")
    private String superAdminEmails;

    boolean alreadySetup = false;

    private final RoleService adminRoleService;
    private final PermissionService permissionService;
    private final UserRepository adminUserRepository;

    public SetupDataLoader(RoleService adminRoleService, PermissionService permissionService, UserRepository adminUserRepository) {
        this.adminRoleService = adminRoleService;
        this.permissionService = permissionService;
        this.adminUserRepository = adminUserRepository;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (alreadySetup)
            return;

        //create all permissions
        permissionService.createPermissionsIfNecessary();

        //get all super-admin permissions
        List<Permission> adminPermissions = getAllSuperAdminPermissions();

        //create role super admin and assign all permissions and columns
        Role adminRole = createSuperAdminRoleIfNotFound(adminPermissions);

        List<User> adminUserList = new ArrayList<>();

        String[] adminEmails = superAdminEmails.split(",");
        for (int i = 0; i < adminEmails.length; i++) {

            // create super Admin
            User superAdmin = createSuperAdmin(adminRole, adminEmails[i], i + 1);
            if (Objects.nonNull(superAdmin)) {
                adminUserList.add(superAdmin);
            }
        }

        // save super admin
        if (!adminUserList.isEmpty()) {
            log.info(":: Saving the first set of Super admin...");
            adminUserRepository.saveAll(adminUserList);
        }

        alreadySetup = true;
    }

    private User createSuperAdmin(Role adminRole, String email, int count) {
        log.info("---- Creating super admin ----");
        if (!adminUserRepository.existsByEmail(email)) {
            User adminUser = new User();
            adminUser.setFirstName(SuperAdminUserConstant.adminUserFirstName);
            adminUser.setLastName(SuperAdminUserConstant.adminUserLastName + "_" + count);
            adminUser.setPhoneNumber(SuperAdminUserConstant.phoneNumber);
            adminUser.setGovtId(SuperAdminUserConstant.govtId);
            adminUser.setPublicKey(SuperAdminUserConstant.publicKey);
            adminUser.setPrivateKey(SuperAdminUserConstant.privateKey);
            adminUser.setEmail(email);
            adminUser.setRole(adminRole);
            return adminUser;
        }

        return null;
    }

    @Transactional
    List<Permission> getAllSuperAdminPermissions() {
        return permissionService.getPermissionsByPermissionType(PermissionType.SUPER);
    }

    @Transactional
    Role createSuperAdminRoleIfNotFound(List<Permission> permissions) {

        Role adminRole = adminRoleService.getRoleByName(SuperAdminUserConstant.adminUserRole);
        if (adminRole == null) {
            adminRole = new Role();
            adminRole = adminRoleService.addRole(adminRole, SuperAdminUserConstant.adminUserRole, permissions);
        }
        return adminRole;

    }
}