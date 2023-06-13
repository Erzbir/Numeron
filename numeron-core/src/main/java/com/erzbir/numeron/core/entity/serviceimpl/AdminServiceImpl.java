package com.erzbir.numeron.core.entity.serviceimpl;

import com.erzbir.numeron.api.entity.AdminService;
import com.erzbir.numeron.core.bot.BotServiceImpl;
import net.mamoe.mirai.contact.MemberPermission;
import net.mamoe.mirai.contact.NormalMember;
import net.mamoe.mirai.event.GlobalEventChannel;
import net.mamoe.mirai.event.events.MemberPermissionChangeEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Erzbir
 * @Date: 2023/4/27 11:24
 */
public class AdminServiceImpl implements AdminService {
    private static final Map<Long, Map<Long, List<Long>>> adminMap = new HashMap<>();

    static {
        BotServiceImpl botService = new BotServiceImpl();
        GroupServiceImpl groupService = new GroupServiceImpl();
        botService.getBotList().forEach(v -> {
                    List<Long> list = new ArrayList<>();
                    HashMap<Long, List<Long>> map = new HashMap<>();
                    v.getGroups()
                            .stream()
                            .filter(f -> groupService.exist(f.getId()))
                            .forEach(g -> {
                                g.getMembers()
                                        .stream()
                                        .filter(t -> t.getPermission().getLevel() > 0)
                                        .toList()
                                        .forEach(c -> list.add(c.getId()));
                                map.put(g.getId(), list);
                            });
                    adminMap.put(v.getId(), map);
                }
        );
        refresh();
    }

    private static void refresh() {
        GlobalEventChannel.INSTANCE.subscribeAlways(MemberPermissionChangeEvent.class, event -> {
            long id = event.getGroup().getId();
            NormalMember member = event.getMember();
            if (member.getPermission().equals(MemberPermission.ADMINISTRATOR)) {
                adminMap.get(event.getBot().getId()).get(id).add(member.getId());
            } else if (member.getPermission().equals(MemberPermission.MEMBER)) {
                adminMap.get(event.getBot().getId()).get(id).add(member.getId());
            }
        });
    }

    @Override
    public List<Long> getAdmins(long botId, long groupId) {
        return adminMap.get(botId).get(groupId);
    }

    @Override
    public boolean exist(long botId, long groupId, long id) {
        return adminMap.get(botId).get(groupId).contains(id);
    }

    @Override
    public boolean exist(long botId, long id) {
        for (List<Long> list : adminMap.get(botId).values()) {
            if (list.contains(id)) {
                return true;
            }
        }
        return false;
    }
}
