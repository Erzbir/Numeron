package com.erzbir.numeron.api.entity;

import java.util.Set;

/**
 * @author Erzbir
 * @Date: 2023/4/27 11:25
 */
public interface BlackService {
    Set<Long> getBlacks();

    boolean exist(long qq);

    boolean addBlack(long qq, long opId);

    boolean removeBlack(long qq);
}
