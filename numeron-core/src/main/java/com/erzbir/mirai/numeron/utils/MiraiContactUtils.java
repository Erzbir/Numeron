package com.erzbir.mirai.numeron.utils;

import com.erzbir.mirai.numeron.entity.NumeronBot;
import net.mamoe.mirai.contact.ContactList;
import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.contact.Group;

/**
 * @author Erzbir
 * @Date: 2023/3/7 17:50
 */
public class MiraiContactUtils {
    private static final ContactList<Friend> friendList = NumeronBot.INSTANCE.getBot().getFriends();
    private static final ContactList<Group> groupList = NumeronBot.INSTANCE.getBot().getGroups();

    public static Group getGroup(long id) {
        return groupList.get(id);
    }

    public static Friend getFriend(long id) {
        return friendList.get(id);
    }
}
