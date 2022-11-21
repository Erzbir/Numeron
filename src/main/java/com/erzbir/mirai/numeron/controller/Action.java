package com.erzbir.mirai.numeron.controller;

/**
 * @author Erzbir
 * @Date: 2022/11/13 17:38
 * <br>
 * 此抽象类定义数据增删查的方法, 有空写改, 请涉及到例如添加黑名单操作时继承此类实现, 对数据库的操作也是
 */
public abstract class Action {

    public abstract String add(Object o);

    public abstract String query(Object o);

    public abstract String remove(Object o);
}
