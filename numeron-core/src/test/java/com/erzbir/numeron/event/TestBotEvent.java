package com.erzbir.numeron.event;


import com.erzbir.numeron.bot.Bot;
import com.erzbir.numeron.component.Component;
import com.erzbir.numeron.event.bot.BotEvent;


/**
 * @author Erzbir
 * @Data: 2024/2/8 03:47
 */
public class TestBotEvent extends AbstractEvent implements BotEvent {
    public TestBotEvent(Object source) {
        super(source);
    }

    @Override
    public Object getSource() {
        return source;
    }

    @Override
    public Bot getBot() {
        return new Bot() {
            @Override
            public String getName() {
                return "hahaha";
            }

            @Override
            public Component getComponent() {
                return null;
            }

            @Override
            public void start() {

            }

            @Override
            public void join() {

            }

            @Override
            public void cancel() {

            }

            @Override
            public long getId() {
                return 0;
            }
        };
    }
}
