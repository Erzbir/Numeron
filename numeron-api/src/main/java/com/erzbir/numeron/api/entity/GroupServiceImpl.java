package com.erzbir.numeron.api.entity;

import java.util.ServiceLoader;

/**
 * @author Erzbir
 * @Date: 2023/6/12 00:49
 */
public class GroupServiceImpl {
    public static final GroupService INSTANCE = ServiceLoader.load(GroupService.class).findFirst().orElseThrow();
}
