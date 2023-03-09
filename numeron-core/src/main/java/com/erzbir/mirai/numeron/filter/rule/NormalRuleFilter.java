package com.erzbir.mirai.numeron.filter.rule;

import com.erzbir.mirai.numeron.entity.GroupList;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.MessageEvent;

/**
 * @author Erzbir
 * @Date: 2022/11/26 16:03
 * <p>过滤掉为授权的群</p>
 */
public class NormalRuleFilter extends AbstractRuleFilter {

    @Override
    public Boolean filter(MessageEvent event, String text) {
        if (event instanceof GroupMessageEvent event1) {
            return GroupList.INSTANCE.contains(event1.getGroup().getId());
        }
        return true;
    }

}
