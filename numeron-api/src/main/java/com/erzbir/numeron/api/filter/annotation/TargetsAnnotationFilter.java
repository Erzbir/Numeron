package com.erzbir.numeron.api.filter.annotation;

import com.erzbir.numeron.annotation.Targets;
import com.erzbir.numeron.api.filter.AnnotationFilter;
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
public class TargetsAnnotationFilter extends AbstractAnnotationFilter<Targets> implements AnnotationFilter {
    @Override
    public boolean filter(Event event) {
        if (annotation == null) {
            return true;
        }

        long[] bots = annotation.bots();
        long[] senders = annotation.senders();
        long[] groups = annotation.groups();

        if (bots.length == 0 && senders.length == 0 && groups.length == 0) {
            return true;
        }


        return filterSenders(senders, event) && filterGroups(groups, event) && filterBots(bots, event);
    }

    private boolean filterBots(long[] bots, Event event) {
        if (bots.length != 0) {
            if (event instanceof BotEvent botEvent) {
                return Arrays.stream(bots).anyMatch(bot -> bot == botEvent.getBot().getId());
            }
        }
        return true;
    }

    private boolean filterSenders(long[] senders, Event event) {
        if (senders.length != 0) {
            if (event instanceof MessageEvent messageEvent) {
                return Arrays.stream(senders).anyMatch(sender -> sender == messageEvent.getSender().getId());
            } else if (event instanceof UserEvent userEvent) {
                return Arrays.stream(senders).anyMatch(sender -> sender == userEvent.getUser().getId());
            }
        }
        return true;
    }

    private boolean filterGroups(long[] groups, Event event) {
        if (groups.length != 0) {
            if (event instanceof GroupEvent groupEvent) {
                return Arrays.stream(groups).anyMatch(group -> group == groupEvent.getGroup().getId());
            }
        }
        return true;
    }
}
