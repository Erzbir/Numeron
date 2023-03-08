package com.erzbir.mirai.numeron.plugins.rss.config.api;

import com.erzbir.mirai.numeron.plugins.rss.config.RssConfig;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.contact.Group;

/**
 * @author Erzbir
 * @Date: 2023/3/8 13:38
 */
public class EditApi {
    public static void editUrl(String id, String url) {
        RssConfig.getInstance().getRssMap().get(id).setUrl(url);
    }

    public static void editId(String id, long newId) {
        RssConfig.getInstance().getRssMap().get(id).setId(newId);
    }

    public static class EditReceiveListApi {

        public static void addContact(String id, long qqId, Class<? extends Contact> type) {
            if (type.equals(Group.class)) {
                addGroup(id, qqId);
            } else if (type.equals(Friend.class)) {
                addUser(id, qqId);
            }
        }

        public static void addGroup(String id, long groupId) {
            RssConfig.getInstance().getRssMap().get(id).getGroupList().add(groupId);
        }

        public static void deleteGroup(String id, long groupId) {
            RssConfig.getInstance().getRssMap().get(id).getGroupList().remove(groupId);
        }

        public static void addUser(String id, long qqId) {
            RssConfig.getInstance().getRssMap().get(id).getUserList().add(qqId);
        }

        public static void deleteUser(String id, long qqId) {
            RssConfig.getInstance().getRssMap().get(id).getGroupList().remove(qqId);
        }
    }
}
