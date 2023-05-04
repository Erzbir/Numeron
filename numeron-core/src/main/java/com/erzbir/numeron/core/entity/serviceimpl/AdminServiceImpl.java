package com.erzbir.numeron.core.entity.serviceimpl;

import com.erzbir.numeron.api.entity.AdminService;
import com.erzbir.numeron.core.bot.BotMap;
import net.mamoe.mirai.contact.MemberPermission;
import net.mamoe.mirai.contact.NormalMember;
import net.mamoe.mirai.event.GlobalEventChannel;
import net.mamoe.mirai.event.events.MemberPermissionChangeEvent;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Erzbir
 * @Date: 2023/4/27 11:24
 */
public class AdminServiceImpl implements AdminService {
    private final Map<Long, Set<Long>> adminMap = new HashMap<>();

    public AdminServiceImpl() {
        BotMap.INSTANCE.forEach((k, v) -> v.getGroups()
                .stream()
                .filter(f -> GroupServiceImpl.INSTANCE.exist(f.getId()))
                .forEach(g -> {
                    Set<Long> set = new HashSet<>();
                    g.getMembers()
                            .stream()
                            .filter(t -> t.getPermission().getLevel() > 0)
                            .toList()
                            .forEach(c -> set.add(c.getId()));
                    adminMap.put(k, set);
                }));
        refresh();
    }

    @Override
    public Set<Long> getAdmins(long id) {
        return adminMap.get(id);
    }

    @Override
    public boolean exist(long qq) {
        AtomicBoolean flag = new AtomicBoolean(false);
        adminMap.forEach((k, v) -> {
            boolean contains = v.contains(qq);
            flag.set(contains);
        });
        return flag.get();
    }

    private void refresh() {
        GlobalEventChannel.INSTANCE.subscribeAlways(MemberPermissionChangeEvent.class, event -> {
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
