package com.erzbir.numeron.entity;

/**
 * @author Erzbir
 * @Data: 2024/2/7 19:00
 */
public interface Role {
    boolean isAdmin();

    long getId();

    String getName();
}