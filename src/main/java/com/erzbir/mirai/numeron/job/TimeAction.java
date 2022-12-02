package com.erzbir.mirai.numeron.job;

/**
 * @author Erzbir
 * @Date: 2022/12/2 12:08
 */
public interface TimeAction {
    void run();

    void setId(String id);

    void setName(String name);
}
