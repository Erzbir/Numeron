package com.erzbir.mirai.numeron.entity;

import net.mamoe.mirai.contact.MemberPermission;
import net.mamoe.mirai.contact.NormalMember;
import net.mamoe.mirai.event.events.MemberPermissionChangeEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Erzbir
 * @Date: 2023/3/8 14:55
 */
public final class AdminList {
    public static final AdminList INSTANCE = new AdminList();
    private final HashMap<Long, List<Long>> adminMap = new HashMap<>();

    public AdminList() {
        NumeronBot.INSTANCE.getBot().getGroups()
                .stream()
                .filter(f -> GroupList.INSTANCE.contains(f.getId()))
                .forEach(g -> {
                    List<Long> list = new ArrayList<>();
                    g.getMembers()
                            .stream()
                            .filter(t -> t.getPermission().getLevel() > 0)
                            .toList()
                            .forEach(v -> list.add(v.getId()));
                    adminMap.put(g.getId(), list);
                });
        refresh();
    }

    public List<Long> getAdmins(long groupId) {
        return adminMap.get(groupId);
    }

    public HashMap<Long, List<Long>> getAdminMap() {
        return adminMap;
    }

    private void refresh() {
        NumeronBot.INSTANCE.getBot().getEventChannel().subscribeAlways(MemberPermissionChangeEvent.class, event -> {
            long id = event.getGroup().getId();
            NormalMember member = event.getMember();
            if (member.getPermission().equals(MemberPermission.ADMINISTRATOR)) {
                adminMap.get(id).add(member.getId());
            } else if (member.getPermission().equals(MemberPermission.MEMBER)) {
                adminMap.get(id).add(member.getId());
            }
        });
    }
}
