package com.erzbir.numeron.api.entity;

import java.util.ServiceLoader;

/**
 * @author Erzbir
 * @Date: 2023/6/12 00:48
 */
public class WhiteServiceImpl {
    public static final WhiteService INSTANCE = ServiceLoader.load(WhiteService.class).findFirst().orElseThrow();
}
