package com.evoter.permission.enums;

public enum PermissionName {
    CREATE_USER(PermissionType.SUPER), VIEW_USER(PermissionType.SUPER),
    VIEW_PERMISSION(PermissionType.SUPER),
    CREATE_ROLE(PermissionType.SUPER), UPDATE_ROLE(PermissionType.SUPER), DELETE_ROLE(PermissionType.SUPER), VIEW_ROLE(PermissionType.SUPER),
    CREATE_CANDIDATE(PermissionType.SUPER), VIEW_CANDIDATE(PermissionType.SUPER),
    CREATE_PARTY(PermissionType.SUPER), VIEW_PARTY(PermissionType.SUPER),
    CREATE_POLL_TYPE(PermissionType.SUPER), VIEW_POLL_TYPE(PermissionType.SUPER),
    CREATE_POLL(PermissionType.SUPER), VIEW_POLL(PermissionType.SUPER);

    public PermissionType permissionType;

    PermissionName(PermissionType permissionType) {
        this.permissionType = permissionType;
    }
}
