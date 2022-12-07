package com.erzbir.mirai.numeron.job.inter;

import net.mamoe.mirai.contact.User;
import org.jetbrains.annotations.NotNull;

/**
 * @author Erzbir
 * @Date: 2022/12/5 18:15
 */
public interface UserTimeAction extends TimeAction {
    User[] getUsers();

    void setUsers(@NotNull User[] users);

}
