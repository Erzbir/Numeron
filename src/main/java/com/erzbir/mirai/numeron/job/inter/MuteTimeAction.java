package com.erzbir.mirai.numeron.job.inter;

import net.mamoe.mirai.contact.Group;
import org.jetbrains.annotations.NotNull;

/**
 * @author Erzbir
 * @Date: 2022/12/7 20:36
 */
public interface MuteTimeAction extends TimeAction {
    void mute(int time);

    void mute(@NotNull Group group);

    void unmute(@NotNull Group group);
}
