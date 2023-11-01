package com.erzbir.numeron.console.plugin;

import com.erzbir.numeron.annotation.CommonFilter;
import com.erzbir.numeron.annotation.Handler;
import com.erzbir.numeron.annotation.Listener;
import com.erzbir.numeron.annotation.MessageFilter;
import com.erzbir.numeron.api.filter.annotation.CommonAnnotationChannelFilter;
import com.erzbir.numeron.api.filter.annotation.MessageAnnotationChannelFilter;
import com.erzbir.numeron.core.context.AppContext;
import com.erzbir.numeron.core.register.EventHandlerRegister;
import net.mamoe.mirai.event.ConcurrencyKind;
import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.EventPriority;
import net.mamoe.mirai.event.GlobalEventChannel;
import net.mamoe.mirai.event.events.BotEvent;
import net.mamoe.mirai.event.events.MessageEvent;

import java.util.Arrays;

/**
 * @author Erzbir
 * @Date: 2023/6/30 11:59
 */
public class PluginEventRegister {
    public static final PluginEventRegister INSTANCE = new PluginEventRegister();

    private PluginEventRegister() {

    }

    public void register(PluginContext pluginContext) {
        EventChannel<BotEvent> eventEventChannel = GlobalEventChannel.INSTANCE
                .filterIsInstance(BotEvent.class).parentScope(pluginContext.getPlugin());
        pluginContext.getClasses().stream().filter(t -> t.isAnnotationPresent(Listener.class)).forEach(t -> {
            Arrays.stream(t.getDeclaredMethods())
                    .filter(method -> method.isAnnotationPresent(Handler.class))
                    .forEach(method -> {
                        Handler handlerAnnotation = method.getAnnotation(Handler.class);
                        EventPriority priority = handlerAnnotation.priority();
                        ConcurrencyKind concurrency = handlerAnnotation.concurrency();
                        CommonFilter commonFilter = method.getAnnotation(CommonFilter.class);
                        EventChannel<BotEvent> filterChannel = eventEventChannel;
                        if (commonFilter != null) {
                            CommonAnnotationChannelFilter filter = new CommonAnnotationChannelFilter();
                            filterChannel = eventEventChannel.filter(filter::filter);
                        } else {
                            MessageFilter annotation = method.getAnnotation(MessageFilter.class);
                            if (annotation != null) {
                                MessageAnnotationChannelFilter filter = new MessageAnnotationChannelFilter();
                                filter.setAnnotation(annotation);
                                filterChannel = eventEventChannel.filter(event -> event instanceof MessageEvent messageEvent && filter.filter(messageEvent));
                            }
                        }
                        Object object = AppContext.INSTANCE.getBean(t.getName());
                        EventHandlerRegister.INSTANCE.register(object, method, filterChannel, priority, concurrency);
                    });
        });
    }
}
