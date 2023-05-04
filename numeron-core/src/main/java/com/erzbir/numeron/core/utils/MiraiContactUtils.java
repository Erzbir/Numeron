package com.erzbir.numeron.core.utils;

import com.erzbir.numeron.core.bot.NumeronBot;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.ContactList;
import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.contact.Group;

/**
 * @author Erzbir
 * @Date: 2023/3/7 17:50
 * <p>用于获取联系人</p>
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

    public static Contact getContact(long id, Class<? extends Contact> type) {
        if (type.equals(Group.class)) {
            return getGroup(id);
        } else if (type.equals(Friend.class)) {
            return getFriend(id);
        }
        return null;
    }
}
