package com.erzbir.mirai.numeron.job.inter;

import net.mamoe.mirai.contact.Group;
import org.jetbrains.annotations.NotNull;

/**
 * @author Erzbir
 * @Date: 2022/12/5 18:11
 */
public interface GroupTimeAction extends TimeAction {
    Group[] getGroups();

    void setGroups(@NotNull Group[] groups);
}
