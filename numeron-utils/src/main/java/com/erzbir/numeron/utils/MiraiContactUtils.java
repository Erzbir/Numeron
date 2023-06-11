package com.erzbir.numeron.utils;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.ContactList;
import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.contact.Group;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Erzbir
 * @Date: 2023/3/7 17:50
 * <p>用于获取联系人</p>
 */
public class MiraiContactUtils {
    private static final ContactList<Friend> friendList;
    private static final ContactList<Group> groupList;

    static {
        List<Bot> botList = Bot.getInstances();
        groupList = botList.stream().flatMap(bot -> bot.getGroups().stream()).collect(Collectors.toCollection(ContactList::new));
        friendList = botList.stream().flatMap(bot -> bot.getFriends().stream()).collect(Collectors.toCollection(ContactList::new));
    }

    private MiraiContactUtils() {

    }

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
