package com.evoter.user.repository;

import com.evoter.user.imodel.UserBasicInfoI;
import com.evoter.user.imodel.UserSettingsI;
import com.evoter.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

//    boolean existsByEmailAndEnabled(String email, boolean enabled);

    User findByEmail(String email);

    boolean existsByRole_Id(Long role_id);

    boolean existsByRole_Name(String name);

    boolean existsByPrivateKey(String privateKey);
    boolean existsByPublicKey(String privateKey);

    boolean existsByGovtId(String govtId);

    @Query(value = "select au.id, au.email, au.phone_number, au.first_name, au.last_name from admin_user au\n" +
            "    inner join roles_permissions arp on au.role_id = arp.role_id\n" +
            "    inner join permission p on p.id = arp.permission_id\n" +
            "where p.name = ?1 AND au.enabled = 1", nativeQuery = true)
    List<UserBasicInfoI> getUsersWithPermission(String permissionName);

//    List<User> findByOrderByLastModifiedDateDesc();

    @Query(value = "select m.private_key, m.public_key from user m where m.email = ?1", nativeQuery = true)
    UserSettingsI getUserSettings(String email);

}
