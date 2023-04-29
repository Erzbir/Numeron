package com.erzbir.numeron.api.entity;

import java.util.ServiceLoader;
import java.util.Set;

/**
 * @author Erzbir
 * @Date: 2023/4/27 11:25
 */
public interface BlackService {
    BlackService INSTANCE = ServiceLoader.load(BlackService.class).findFirst().get();

    Set<Long> getBlacks();

    boolean exist(long qq);

    boolean addBlack(long qq, long opId);

    boolean removeBlack(long qq);
}
