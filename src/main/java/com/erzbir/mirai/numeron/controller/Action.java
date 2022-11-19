package com.erzbir.mirai.numeron.controller;

/**
 * @author Erzbir
 * @Date: 2022/11/13 17:38
 */
public abstract class Action {

    public abstract void add(long id);

    public abstract String query(long id);

    public abstract void remove(long id);
}
