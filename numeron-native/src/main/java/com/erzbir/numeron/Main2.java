package com.erzbir.numeron;

import com.erzbir.numeron.event.*;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Erzbir
 * @Data: 2024/2/20 17:07
 */
@Slf4j
public class Main2 {
    public static void main(String[] args) throws InterruptedException {
        EventDispatcher eventDispatcher = new NotificationEventDispatcher();
        EventChannel<NamedEvent> eventEventChannel = GlobalEventChannel.INSTANCE.filterInstance(NamedEvent.class);
        eventDispatcher.start();
        eventEventChannel.subscribe(NamedEvent.class, event -> {
            log.info("Name: {}", event.getName());
            return StandardListenerResult.CONTINUE;
        });
        EventChannel<Event> filter = GlobalEventChannel.INSTANCE.filter(event -> event instanceof TestEvent);
        filter.subscribe(Event.class, event -> {
            if (event instanceof TestEvent) {
                log.info(((TestEvent) event).getStr());
            }
            return StandardListenerResult.CONTINUE;
        });
        GlobalEventChannel.INSTANCE.subscribe(TestNamedEvent.class, event -> {
            log.info("this is a TestNamedEvent");
            return StandardListenerResult.CONTINUE;
        });
        GlobalEventChannel.INSTANCE.subscribe(Event.class, event -> {
            log.info("this is an Event");
            return StandardListenerResult.CONTINUE;
        });
        eventDispatcher.dispatch(new TestNamedEvent("this", "test1"));
        eventDispatcher.dispatch(new TestNamedEvent("this", "test2"));
        eventDispatcher.dispatch(new TestNamedEvent("this", "test3"), eventEventChannel);
        eventDispatcher.dispatch(new TestNamedEvent("this", "test4"));
        eventDispatcher.dispatch(new TestEvent("this"));
        eventDispatcher.dispatch(new TestNamedEvent("this", "test5"), eventEventChannel);
        Thread.sleep(2000);
        eventDispatcher.cancel();
    }
}
