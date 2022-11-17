package com.erzbir.mirai.numeron.manage;

import net.mamoe.mirai.contact.ContactList;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.GroupSettings;
import net.mamoe.mirai.contact.Member;

import java.util.List;
import java.util.Objects;

/**
 * @Author: Erzbir
 * @Date: 2022/11/9 18:58
 */
public class Mute {

    public static void muteLots(int time, Member... members) {
        for (Member member : members) {
            if (member.getPermission().getLevel() == 0) {
                try {
                    member.mute(time);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void openAll(ContactList<Group> groups, long... ids) {
        for (long id : ids) {
            if (groups.contains(id)) {
                GroupSettings groupSettings = Objects.requireNonNull(groups.get(id)).getSettings();
                try {
                    groupSettings.setMuteAll(false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void openAll(ContactList<Group> groups, List<Long> ids) {
        for (long id : ids) {
            if (groups.contains(id)) {
                GroupSettings groupSettings = Objects.requireNonNull(groups.get(id)).getSettings();
                try {
                    groupSettings.setMuteAll(false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void muteAll(ContactList<Group> groups, long... ids) {
        for (long id : ids) {
            if (groups.contains(id)) {
                GroupSettings groupSettings = Objects.requireNonNull(groups.get(id)).getSettings();
                try {
                    groupSettings.setMuteAll(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void muteAll(ContactList<Group> groups, List<Long> list) {
        for (long id : list) {
            if (groups.contains(id)) {
                Group group = groups.get(id);
                GroupSettings groupSettings = Objects.requireNonNull(group).getSettings();
                try {
                    groupSettings.setMuteAll(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static boolean botIsAdmin(Group group) {
        return group.getBotPermission().getLevel() > 0;
    }
}
