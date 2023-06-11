package com.erzbir.numeron.api.entity;

import java.util.ServiceLoader;

/**
 * @author Erzbir
 * @Date: 2023/6/12 00:48
 */
public class AdminServiceImpl {
    public static final AdminService INSTANCE = ServiceLoader.load(AdminService.class).findFirst().orElseThrow();
}
