package com.erzbir.mirai.numeron.job.inter;

import net.mamoe.mirai.contact.Friend;
import org.jetbrains.annotations.NotNull;

/**
 * @author Erzbir
 * @Date: 2022/12/5 18:17
 */
public interface FriendTimeAction extends TimeAction {
    Friend[] getFriends();

    void setFriends(@NotNull Friend[] friends);
}
