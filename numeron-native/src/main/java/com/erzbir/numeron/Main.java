package com.erzbir.numeron;

import com.erzbir.numeron.bot.Bot;
import com.erzbir.numeron.event.*;
import com.erzbir.numeron.event.bot.BotEvent;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public class Main {
    private static final AtomicBoolean intercept = new AtomicBoolean(false);

    public static void main(String[] args) {
        start();
        while (!intercept.get()) {
        }
    }

    public static void start() {
        Thread.ofVirtual().start(() -> {
            PollingEventDispatcher eventDispatcher = new PollingEventDispatcher();
            EventChannel<BotEvent> eventEventChannel = GlobalEventChannel.INSTANCE.filterInstance(BotEvent.class);
            eventDispatcher.start();
            eventEventChannel.subscribe(BotEvent.class, event -> {
//            while (!Thread.currentThread().isInterrupted()) {
                Bot bot = event.getBot();
                log.info("Bot name: {}", bot.getName());
//            }
                return StandardListenerResult.CONTINUE;
            });
            EventChannel<Event> filter = GlobalEventChannel.INSTANCE.filter(event -> event instanceof TestEvent);
            ListenerHandle subscribe = filter.subscribe(Event.class, event -> {
                if (event instanceof TestEvent) {
                    log.info(((TestEvent) event).getStr());
                }
                return StandardListenerResult.CONTINUE;
            });
            GlobalEventChannel.INSTANCE.filter(event -> false).subscribe(TestEvent.class, event -> {
                log.info("1234r432");
                return StandardListenerResult.CONTINUE;
            });
            Thread.ofVirtual().start(() -> {
                while (!intercept.get()) {
                    eventDispatcher.dispatch(new TestBotEvent("this"));
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ignore) {

                    }
                }
            });
            Thread.ofVirtual().start(() -> {
                while (!intercept.get()) {
                    eventDispatcher.dispatch(new TestEvent("this"));
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ignore) {

                    }
                }
            });
        });
    }
}