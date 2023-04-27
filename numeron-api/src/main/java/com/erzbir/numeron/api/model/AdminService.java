package com.erzbir.numeron.api.model;

import java.util.ServiceLoader;
import java.util.Set;

/**
 * @author Erzbir
 * @Date: 2023/4/27 11:24
 */
public interface AdminService {
    AdminService INSTANCE = ServiceLoader.load(AdminService.class).findFirst().get();

    Set<Long> getAdmins(long groupId);

    boolean exist(long qq);
}
