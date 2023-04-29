package com.erzbir.numeron.api.entity;

import java.util.ServiceLoader;
import java.util.Set;

/**
 * @author Erzbir
 * @Date: 2023/4/27 11:29
 */
public interface WhiteService {
    WhiteService INSTANCE = ServiceLoader.load(WhiteService.class).findFirst().get();

    Set<Long> getAdminList();

    boolean exist(long qq);

    boolean addWhite(long qq, long opId);

    boolean removeWhite(long qq);

}
