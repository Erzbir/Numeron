package com.erzbir.mirai.numeron.job;

import lombok.Getter;
import lombok.Setter;
import net.mamoe.mirai.contact.Group;

/**
 * @author Erzbir
 * @Date: 2022/12/2 12:37
 */
@Getter
@Setter
public class MuteAction extends AbstractAction {
    private Group group;

    public MuteAction(String name) {
        super(name);
    }

    @Override
    public void run() {
        group.getSettings().setMuteAll(true);
    }
}
