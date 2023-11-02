package com.erzbir.numeron.api.permission;

/**
 * <p>权限</p>
 *
 * @author Erzbir
 * @Date: 2022/11/19 09:21
 */
public enum PermissionType implements Permission {
    MASTER(0x00000000, "master"),
    WHITE(0x00000001, "white"),

    ADMIN(0x00000002, "admin"),

    FRIEND(0x00000003, "friend"),
    NORMAL(0x00000004, "normal"),
    EVERYONE(0x0FFFFFF0, "everyone"),
    NONE(0x0FFFFFFF, "none");

    private final int code;
    private final String name;

    PermissionType(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static PermissionType valueOf(int code) {
        if (code < 0) {
            return NORMAL;
        }
        if (code > 0x00000004) {
            return NONE;
        }
        for (PermissionType permissionType : PermissionType.values()) {
            if (permissionType.getCode() == code) {
                return permissionType;
            }
        }
        throw new IllegalArgumentException("No enum constant " + code);
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getName() {
        return name;
    }
}
