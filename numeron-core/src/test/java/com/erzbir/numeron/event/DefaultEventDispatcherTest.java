package com.erzbir.numeron.event;

import com.erzbir.numeron.bot.Bot;
import com.erzbir.numeron.event.bot.BotEvent;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class DefaultEventDispatcherTest {

    @Test
    void dispatch() throws InterruptedException {
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
                log.info(((TestEvent) event).getName());
            }
            return StandardListenerResult.CONTINUE;
        });
        GlobalEventChannel.INSTANCE.filter(event -> false).subscribe(TestEvent.class, event -> {
            log.info("1234r432");
            return StandardListenerResult.CONTINUE;
        });
        eventDispatcher.dispatch(new TestBotEvent(this), eventEventChannel);
        eventDispatcher.dispatch(new TestBotEvent(new Object()), GlobalEventChannel.INSTANCE);
        eventDispatcher.dispatch(new TestBotEvent("assas"), eventEventChannel);
        eventDispatcher.dispatch(new TestBotEvent(this), eventEventChannel);
        eventDispatcher.dispatch(new TestBotEvent(this), GlobalEventChannel.INSTANCE);
        eventDispatcher.dispatch(new TestEvent(this), filter);

    }


}