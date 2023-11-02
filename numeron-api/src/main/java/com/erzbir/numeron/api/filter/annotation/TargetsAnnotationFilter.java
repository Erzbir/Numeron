package com.erzbir.numeron.api.filter.annotation;

import com.erzbir.numeron.annotation.Targets;
import com.erzbir.numeron.api.filter.Filter;
import net.mamoe.mirai.event.Event;
import net.mamoe.mirai.event.events.BotEvent;
import net.mamoe.mirai.event.events.GroupEvent;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.event.events.UserEvent;

import java.util.Arrays;

/**
 * 针对 {@link Targets} 注解的内容过滤
 *
 * @author Erzbir
 * @Date 2023/11/1
 * @see Targets
 */
public class TargetsAnnotationFilter extends AbstractAnnotationChannelFilter<Targets, Event> implements Filter<Event> {
    @Override
    public boolean filter(Event event) {
        if (annotation == null) {
            return true;
        }

        boolean match = true;

        long[] bots = annotation.bots();
        long[] senders = annotation.senders();
        long[] groups = annotation.groups();

        if (bots.length == 0 && senders.length == 0 && groups.length == 0) {
            return true;
        }

        if (bots.length != 0) {
            if (event instanceof BotEvent botEvent) {
                match &= Arrays.stream(bots).anyMatch(bot -> bot == botEvent.getBot().getId());
            }

        }

        if (senders.length != 0) {
            if (event instanceof UserEvent userEvent) {
                match &= Arrays.stream(senders).anyMatch(sender -> sender == userEvent.getUser().getId());
            }
            if (event instanceof MessageEvent messageEvent) {
                match &= Arrays.stream(senders).anyMatch(sender -> sender == messageEvent.getSender().getId());
            }
        }

        if (groups.length != 0) {
            if (event instanceof GroupEvent groupEvent) {
                match &= Arrays.stream(groups).anyMatch(group -> group == groupEvent.getGroup().getId());
            }
        }
        return match;
    }
}
