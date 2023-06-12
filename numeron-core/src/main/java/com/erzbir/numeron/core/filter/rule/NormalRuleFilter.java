package com.erzbir.numeron.core.filter.rule;

import com.erzbir.numeron.core.entity.serviceimpl.GroupServiceImpl;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.MessageEvent;

/**
 * @author Erzbir
 * @Date: 2022/11/26 16:03
 * <p>过滤掉为授权的群</p>
 */
public class NormalRuleFilter extends AbstractRuleFilter {

    @Override
    public Boolean filter(MessageEvent event) {
        GroupServiceImpl groupService = new GroupServiceImpl();
        if (event instanceof GroupMessageEvent event1) {
            return groupService.exist(event1.getGroup().getId());
        }
        return true;
    }

}
