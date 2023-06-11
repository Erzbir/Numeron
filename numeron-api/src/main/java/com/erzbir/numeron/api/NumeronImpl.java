package com.erzbir.numeron.api;

import java.util.ServiceLoader;

/**
 * @author Erzbir
 * @Date: 2023/6/11 23:15
 */
public class NumeronImpl {
    public static Numeron INSTANCE = ServiceLoader.load(Numeron.class).findFirst().orElseThrow();
}
