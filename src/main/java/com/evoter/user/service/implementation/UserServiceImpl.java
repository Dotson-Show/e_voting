package com.evoter.user.service.implementation;

import com.evoter.exception.GeneralException;
import com.evoter.general.enums.ResponseCodeAndMessage;
import com.evoter.general.service.GeneralService;
import com.evoter.permission.enums.PermissionType;
import com.evoter.permission.model.Permission;
import com.evoter.permission.service.PermissionService;
import com.evoter.role.dto.RoleDTO;
import com.evoter.role.model.Role;
import com.evoter.role.repository.RoleRepository;
import com.evoter.role.service.RoleService;
import com.evoter.user.constants.UserConstant;
import com.evoter.user.dto.*;
import com.evoter.user.imodel.UserBasicInfoI;
import com.evoter.user.imodel.UserSettingsI;
import com.evoter.user.model.User;
import com.evoter.user.repository.UserRepository;
import com.evoter.user.service.UserService;
import com.evoter.util.GeneralUtil;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final GeneralService generalService;
    private final RoleService roleService;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    private final PermissionService permissionService;

    public UserServiceImpl(RoleService adminRoleService, GeneralService generalService, UserRepository adminUserRepository, RoleRepository adminRoleRepository, PermissionService permissionService) {
        this.roleService = adminRoleService;
        this.generalService = generalService;
        this.userRepository = adminUserRepository;
        this.roleRepository = adminRoleRepository;
        this.permissionService = permissionService;
    }

//    @Override
//    public boolean validateAdminUserExistAsAdminAndEnabled(String email) {
//        return userRepository.existsByEmailAndEnabled(email, true);
//    }

    @Override
    public UserListDTO getAllUsers(UserRequestDTO requestDTO) {
        log.info("Getting Admin Users List");

        Pageable paged = generalService.getPageableObject(requestDTO.getSize(), requestDTO.getPage(), Sort.by(Sort.Direction.DESC, "lastModifiedDate"));
        Page<User> superUserPage = userRepository.findAll(paged);

        UserListDTO adminUserListDTO = new UserListDTO();

        List<User> adminUserList = superUserPage.getContent();
        if (superUserPage.getContent().size() > 0) {
            adminUserListDTO.setHasNextRecord(superUserPage.hasNext());
            adminUserListDTO.setTotalCount((int) superUserPage.getTotalElements());
        }

        List<UserDTO> adminUserDTOS = convertToAdminUserDTOList(adminUserList);
        adminUserListDTO.setUserDTOList(adminUserDTOS);

        return adminUserListDTO;
    }

    @Override
    public UserDTO getOneAdminUser(String email) {
        log.info("Getting Admin User using Email => {}", email);

        User adminUser = Optional.ofNullable(userRepository.findByEmail(email)).orElseThrow(() -> new GeneralException(ResponseCodeAndMessage.RECORD_NOT_FOUND_88));

        return getUserDTO(adminUser);
    }

    @Override
    public User getUserForLogin(String email) {
        log.info("Validating that user => {} exist in db ", email);

        User user = userRepository.findByEmail(email);
        if (Objects.isNull(user)) {
            throw new GeneralException(ResponseCodeAndMessage.RECORD_NOT_FOUND_88);
        }
        return user;
    }

    @Override
    public void logoutUser(String email) {
        log.info("Logging out User using Email => {}", email);

        //impl logout

    }


    @Override
    public UserDTO registration(RegistrationRequestDTO createUserDto) {
        log.info("Registration Request with payload = {}", createUserDto);

        //validate first name, last name and phone number
        GeneralUtil.validateNameAndPhoneNumber(createUserDto.getFirstName(), createUserDto.getLastName(), createUserDto.getPhoneNumber());

        // email to lower case
        String email = createUserDto.getEmail().toLowerCase();

        // check that email is not null or empty
        validateEmail(email);

        //get all permissions
        List<Permission> userPermissions = getAllVoterPermissions();

        //create role and assign all permissions
        Role role = createRoleIfNotFound(userPermissions);

        //create new user
        User user = new User();
        user.setFirstName(createUserDto.getFirstName());
        user.setLastName(createUserDto.getLastName());
        user.setEmail(email);
        user.setPhoneNumber(createUserDto.getPhoneNumber());
        user.setRole(role);

        //generated values
        user.setGovtId(generateGovtId(email));
        user.setPrivateKey(generatePrivateKey());
        user.setPublicKey(generatePublicKey());

        // save to db
        User savedAdminUser = saveAdminUser(user);

        // convert to dto
        return getUserDTO(savedAdminUser);
    }

    @Override
    public UserDTO addUser(CreateUpdateUserDTO createUserDto) {
        log.info("creating a user with payload = {}", createUserDto);

        //validate first name, last name and phone number
        GeneralUtil.validateNameAndPhoneNumber(createUserDto.getFirstName(), createUserDto.getLastName(), createUserDto.getPhoneNumber());

        // email to lower case
        String email = createUserDto.getEmail().toLowerCase();

        // check that email is not null or empty
        validateEmail(email);

        //get the role
        Role role = getRole(createUserDto.getRoleId());

        //create new user
        User adminUser = new User();
        adminUser.setFirstName(createUserDto.getFirstName());
        adminUser.setLastName(createUserDto.getLastName());
        adminUser.setEmail(email);
        adminUser.setPhoneNumber(createUserDto.getPhoneNumber());
        adminUser.setRole(role);

        // save to db
        User savedAdminUser = saveAdminUser(adminUser);


        // convert to dto
        return getUserDTO(savedAdminUser);
    }

    @Override
    public UserDTO updateUser(CreateUpdateUserDTO createAdminUserDto, Long adminUserId) {
        log.info("Request to update an admin user with admin user id = {} with payload = {}", adminUserId, createAdminUserDto);

        User adminUser = getAdminUserById(adminUserId);
        Role role = adminUser.getRole();

        // check that role id is not null or empty
        if (!Objects.equals(createAdminUserDto.getRoleId(), adminUser.getRole().getId())) {
            role = getRole(createAdminUserDto.getRoleId());
        }

        adminUser.setRole(role);

        // save to db
        adminUser = saveAdminUser(adminUser);

        // convert to dto
        return getUserDTO(adminUser);
    }


    @Override
    public void validateThatAdminUserDoesNotExist(String email) {
        log.info("Validating if admin user email {} already exist", email);

        validateEmail(email);
    }


    @Override
    public UserDTO getUserDTO(User adminUser) {
        log.info("Converting Admin User to Admin User DTO");

        UserDTO adminUserDTO = new UserDTO();

        generalService.createDTOFromModel(adminUser, adminUserDTO);

        //get role dto
        RoleDTO roleDTO = roleService.getAdminRoleDTO(adminUser.getRole());
        adminUserDTO.setRole(roleDTO);

        return adminUserDTO;
    }

//    @Override
//    public RegistrationResponseDTO getRegistrationDTO(RegistrationRequestDTO adminUser) {
//        log.info("Converting Admin User to Admin User DTO");
//
//        RegistrationResponseDTO adminUserDTO = new RegistrationResponseDTO();
//
//        generalService.createDTOFromModel(adminUser, adminUserDTO);
//
//        //get role dto
//        RoleDTO roleDTO = adminRoleService.getAdminRoleDTO(adminUser.getRole());
//        adminUserDTO.setRole(roleDTO);
//
//        return adminUserDTO;
//    }

    @Override
    public UserListDTO getAllUsersByPermissionName(String permissionName) {
        log.info("Getting Admin Users List with permissionName => {}", permissionName);

        List<UserBasicInfoI> usersWithPermission = userRepository.getUsersWithPermission(permissionName);

        UserListDTO adminUserListDTO = new UserListDTO();
        List<UserDTO> adminUserDTOListWithPermission = new ArrayList<>();

        usersWithPermission.forEach(adminUserBasicInfoI -> {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(adminUserBasicInfoI.id());
            userDTO.setFirstName(adminUserBasicInfoI.firstName());
            userDTO.setLastName(adminUserBasicInfoI.lastName());
            userDTO.setEmail(adminUserBasicInfoI.email());
            userDTO.setPhoneNumber(adminUserBasicInfoI.phoneNumber());
            adminUserDTOListWithPermission.add(userDTO);
        });

        adminUserListDTO.setTotalCount(usersWithPermission.size());
        adminUserListDTO.setUserDTOList(adminUserDTOListWithPermission);
        return adminUserListDTO;
    }

    @Override
    public UserSettingsDTO getUserSettings(String email) {
        log.info("Getting settings for user => {}", email);

        if (GeneralUtil.stringIsNullOrEmpty(email)) {
            throw new GeneralException(ResponseCodeAndMessage.RECORD_NOT_FOUND_88.responseCode,
                    "Merchant not found");
        }

        UserSettingsI userSettings = userRepository.getUserSettings(email);

        UserSettingsDTO userSettingsDTO = new UserSettingsDTO();
        userSettingsDTO.setPublicKey(userSettings.publicKey());
        userSettingsDTO.setPrivateKey(userSettings.privateKey());

        return userSettingsDTO;
    }

    @Transactional
    List<Permission> getAllVoterPermissions() {
        return permissionService.getPermissionsByPermissionType(PermissionType.USER);
    }

    @Transactional
    Role createRoleIfNotFound(List<Permission> permissions) {

        Role adminRole = roleService.getRoleByName(UserConstant.userRole);
        if (adminRole == null) {
            adminRole = new Role();
            adminRole = roleService.addRole(adminRole, UserConstant.userRole, permissions);
        }
        return adminRole;

    }

    static String generateGovtId(String email) {
        log.info("Generating Govt Id for {}", email);

        email = email.replace(" ", "").toUpperCase(Locale.ROOT);

        String merchantId;
        if (email.length() > 4) {
            merchantId = email.substring(0, 4) + generateRandomNumbers().substring(0, 6);
        } else {
            merchantId = email + generateRandomNumbers().substring(0, 10 - email.length());
        }

        //check if merchantId is available
        return merchantId;
    }


    private String generatePrivateKey() {
        log.info("Generating private key");
        String privateKey = generateUniqueKey() + "_EVOTE";

        //check if generated key exist on the system
        if (userRepository.existsByPrivateKey(privateKey)) {
            //call generate private Key again
            return generatePrivateKey();
        }

        //if false return privateKey
        return privateKey;
    }

    private String generatePublicKey() {
        log.info("Generating public key");
        String publicKey = generateUniqueKey() + "_EVOTE";

        //check if generated key exist on the system
        if (userRepository.existsByPublicKey(publicKey)) {
            //call generate public key again
            return generatePublicKey();
        }

        //if false return publicKey
        return publicKey;
    }


    static String generateUniqueKey() {
        // chose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder s = new StringBuilder(32);
        int y;
        for (y = 0; y < 32; y++) {

            // generating a random number
            int index
                    = (int) (AlphaNumericString.length()
                    * Math.random());
            // add Character one by one in end of s
            s.append(AlphaNumericString
                    .charAt(index));
        }
        return s.toString();
    }

    private List<UserDTO> convertToAdminUserDTOList(List<User> adminUserList) {
        log.info("Converting Admin User List to Admin User DTO List");

        return adminUserList.stream().map(this::getUserDTO).collect(Collectors.toList());
    }

    private void validateEmail(String email) {

        if (GeneralUtil.stringIsNullOrEmpty(email)) {
            log.info("Admin User email {} is invalid", email);
            throw new GeneralException(ResponseCodeAndMessage.INCOMPLETE_PARAMETERS_91.responseCode, "Admin User Email " + email + " is invalid!");
        }

        if (userRepository.existsByEmail(email)) {
            throw new GeneralException(ResponseCodeAndMessage.ALREADY_EXIST_86.responseCode, "Email already exist");
        }
    }

    private Role getRole(Long roleId) {
        if (Objects.isNull(roleId)) {
            throw new GeneralException(ResponseCodeAndMessage.INCOMPLETE_PARAMETERS_91.responseCode, "Role id cannot be empty!");
        }

        return roleRepository.findById(roleId).orElseThrow(() -> new GeneralException(ResponseCodeAndMessage.RECORD_NOT_FOUND_88.responseCode, "Role id cannot be empty"));
    }

    private User getAdminUserById(Long adminUserId) {
        log.info("Getting admin user by adminUserId {}", adminUserId);

        // check that admin user id is not null or empty
        if (!Objects.nonNull(adminUserId)) {
            throw new GeneralException(ResponseCodeAndMessage.INCOMPLETE_PARAMETERS_91.responseCode, "admin user id cannot be null or empty!");
        }

        return userRepository.findById(adminUserId).orElseThrow(() -> new GeneralException(ResponseCodeAndMessage.RECORD_NOT_FOUND_88.responseCode, "Admin User not found"));
    }

    private User saveAdminUser(User adminUser) {
        log.info("::: saving admin user to db :::");
        return userRepository.save(adminUser);
    }

    static String generateRandomNumbers() {
        double a = Math.random();
        return Double.toString(a).split("\\.")[1];
    }
}