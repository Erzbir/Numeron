package com.erzbir.numeron.utils;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.ContactList;
import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.contact.Group;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Erzbir
 * @Date: 2023/3/7 17:50
 * <p>用于获取联系人</p>
 */
public class MiraiContactUtils {
    private static final Map<Long, ContactList<Friend>> friendMap = new HashMap<>();
    private static final Map<Long, ContactList<Group>> groupMap = new HashMap<>();

    static {
        List<Bot> botList = Bot.getInstances();
        botList.forEach(t -> {
            friendMap.put(t.getId(), t.getFriends());
            groupMap.put(t.getId(), t.getGroups());
        });
    }

    private MiraiContactUtils() {

    }

    public static Group getGroup(long botId, long id) {
        return (Group) getContact(botId, id, Group.class);
    }

    public static Friend getFriend(long botId, long id) {
        return (Friend) getContact(botId, id, Friend.class);
    }

    public static Group getGroup(long id) {
        return (Group) getContact(id, Group.class);
    }

    public static Friend getFriend(long id) {
        return (Friend) getContact(id, Friend.class);
    }

    public static Contact getContact(long id, Class<? extends Contact> type) {
        for (Bot bot : Bot.getInstances()) {
            Contact contact = getContact(bot.getId(), id, type);
            if (contact != null) {
                return contact;
            }
        }
        return null;
    }

    public static Contact getContact(long botId, long id, Class<? extends Contact> type) {
        if (type.equals(Group.class)) {
            return groupMap.get(botId).get(id);
        } else if (type.equals(Friend.class)) {
            return friendMap.get(botId).get(id);
        }
        return null;
    }
}
