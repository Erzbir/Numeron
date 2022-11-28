package com.erzbir.mirai.numeron.controller;

/**
 * @author Erzbir
 * @Date: 2022/11/13 17:38
 */
public interface Action {

    void add(Object o, String name, Long op_id);

    String query(Object o);

    void remove(Object o);
}
