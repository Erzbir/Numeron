package com.erzbir.numeron.core.filter.event.bot;

import com.erzbir.numeron.core.filter.AbstractBotEventFilter;
import com.erzbir.numeron.core.filter.EventFilter;
import com.erzbir.numeron.core.filter.Filter;
import net.mamoe.mirai.event.events.BotEvent;

/**
 * @author Erzbir
 * @Date: 2023/6/25 15:12
 */
public class BotFilter extends AbstractBotEventFilter<BotEvent> implements EventFilter<BotEvent> {
    public BotFilter(Filter filter) {
        super(filter);
    }

    /**
     * @param event bot 事件
     * @see AbstractBotEventFilter
     */
    @Override
    public void filter(BotEvent event) {
        setFilterRule(t -> filter0(event), event);
    }

    private boolean filter0(BotEvent event) {
        return event.getBot().getId() == botId;
    }

    @Override
    public boolean filter() {
        return super.filter() && filterRule.test(arg);
    }
}