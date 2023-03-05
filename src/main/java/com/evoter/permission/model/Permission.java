package com.evoter.permission.model;

import com.evoter.permission.enums.PermissionType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String name;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
//    @Column(columnDefinition = "bit default 0")
    private boolean userA = false;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
//    @Column(columnDefinition = "bit default 0")
    private boolean superAdmin = true;

    public void setPermissionType(PermissionType permissionType) {
        this.userA = permissionType.equals(PermissionType.USER);
        this.superAdmin = true;
    }

    public PermissionType getPermissionType() {
        return this.userA ? PermissionType.USER : PermissionType.SUPER;
    }
}