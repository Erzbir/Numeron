package com.erzbir.mirai.numeron.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Erzbir
 * @Date: 2023/3/8 14:55
 */
public class AdminList {
    public static AdminList INSTANCE = new AdminList();
    private final HashMap<Long, List<Long>> adminMap = new HashMap<>();

    {
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
    }

    public List<Long> getAdmins(long groupId) {
        return adminMap.get(groupId);
    }

    public HashMap<Long, List<Long>> getAdminMap() {
        return adminMap;
    }
}
