package com.erzbir.mirai.numeron.job;

import lombok.Getter;
import lombok.Setter;
import net.mamoe.mirai.contact.Group;
import org.jetbrains.annotations.NotNull;

/**
 * @author Erzbir
 * @Date: 2022/12/2 12:37
 */
@Getter
@Setter
public class GroupMuteTimeAction extends MuteActionAdapter {
    private static final String id = GroupMuteTimeAction.class.getName();
    private Group[] groups;

    public GroupMuteTimeAction(Group[] groups) {
        setGroups(groups);
    }

    public GroupMuteTimeAction() {
        this(null);
    }

    @Override
    public void execute() {
        for (Group group : groups) {
            mute(group);
        }
    }

    @Override
    public Group[] getGroups() {
        return groups;
    }

    @Override
    public void setGroups(Group[] groups) {
        this.groups = groups;
    }

    @Override
    public void mute(@NotNull Group group) {
        group.getSettings().setMuteAll(true);
    }
}

