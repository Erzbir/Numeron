package com.erzbir.mirai.numeron.job;

import com.erzbir.mirai.numeron.job.inter.GroupTimeAction;
import com.erzbir.mirai.numeron.job.inter.MuteTimeAction;
import lombok.Getter;
import lombok.Setter;
import net.mamoe.mirai.contact.Group;
import org.jetbrains.annotations.NotNull;

/**
 * @author Erzbir
 * @Date: 2022/12/2 12:39
 */
@Getter
@Setter
public class GroupUnMuteTimeAction extends MuteActionAdapter {
    private static final String id = GroupUnMuteTimeAction.class.getName();
    private Group[] groups;

    public GroupUnMuteTimeAction(Group[] groups) {
        setGroups(groups);
    }

    public GroupUnMuteTimeAction() {
        this(null);
    }

    @Override
    public void execute() {
        for (Group group : groups) {
            group.getSettings().setMuteAll(false);
        }
    }

    @Override
    public Group[] getGroups() {
        return new Group[0];
    }

    @Override
    public void setGroups(Group[] groups) {
        this.groups = groups;
    }

    @Override
    public void unmute(@NotNull Group group) {
        group.getSettings().setMuteAll(false);
    }
}

abstract class MuteActionAdapter implements MuteTimeAction, GroupTimeAction {

    @Override
    public Group[] getGroups() {
        return new Group[0];
    }

    @Override
    public void setGroups(@NotNull Group[] groups) {

    }

    @Override
    public void mute(int time) {

    }

    @Override
    public void mute(@NotNull Group group) {

    }

    @Override
    public void unmute(@NotNull Group group) {

    }

    @Override
    public void execute() {

    }
}
