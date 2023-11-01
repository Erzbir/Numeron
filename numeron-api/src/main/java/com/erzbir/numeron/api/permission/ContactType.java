package com.erzbir.numeron.api.permission;

import lombok.Getter;

/**
 * @author Erzbir
 * @Date 2023/11/1
 */
@Getter
public enum ContactType {
    USER(0, "user"),
    GROUP(1, "group");

    private final int code;
    private final String name;

    ContactType(int code, String name) {
        this.name = name;
        this.code = code;
    }

    public static ContactType valueOf(int code) {
        ContactType[] values = ContactType.values();
        for (ContactType contactType : values) {
            if (contactType.code == code) {
                return contactType;
            }
        }
        throw new IllegalArgumentException("No enum constant " + code);
    }
}
