package com.erzbir.mirai.numeron.controller;

/**
 * @author Erzbir
 * @Date: 2022/11/13 17:38
 */
public abstract class Action {

    public abstract void add(Object o);

    public abstract String query(Object o);

    public abstract void remove(Object o);
}
