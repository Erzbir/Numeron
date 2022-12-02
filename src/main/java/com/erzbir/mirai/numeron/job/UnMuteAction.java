package com.erzbir.mirai.numeron.job;

import net.mamoe.mirai.contact.Group;

/**
 * @author Erzbir
 * @Date: 2022/12/2 12:39
 */
public class UnMuteAction extends AbstractAction {
    private Group group;

    public UnMuteAction(String name) {
        super(name);
    }

    @Override
    public void run() {
        group.getSettings().setMuteAll(true);
    }
}
