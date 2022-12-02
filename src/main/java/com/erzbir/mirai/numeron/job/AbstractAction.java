package com.erzbir.mirai.numeron.job;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Erzbir
 * @Date: 2022/12/2 12:10
 */
@Setter
@Getter
public abstract class AbstractAction implements TimeAction {
    protected String id;
    protected String name;

    public AbstractAction(String name) {
        this.name = name;
    }
}
