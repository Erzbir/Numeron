package com.erzbir.numeron.api.entity;

import java.util.ServiceLoader;

/**
 * @author Erzbir
 * @Date: 2023/6/12 00:48
 */
public class BlackServiceImpl {
    public static final BlackService INSTANCE = ServiceLoader.load(BlackService.class).findFirst().orElseThrow();
}
