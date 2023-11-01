package com.erzbir.numeron.api.permission;

import java.io.Serializable;

/**
 * @author Erzbir
 * @Date 2023/11/1
 */
public interface Permission extends Serializable {
    int getCode();

    String getName();
}
