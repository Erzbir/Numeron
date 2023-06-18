package com.erzbir.numeron.api.context;

import java.util.ServiceLoader;

/**
 * @author Erzbir
 * @Date: 2023/6/17 23:25
 */
public class AppContextServiceImpl {
    public static final AppContextService INSTANCE = ServiceLoader.load(AppContextService.class).findFirst().orElseThrow();
}
