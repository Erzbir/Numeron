package com.erzbir.numeron.event;

import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * 基于轮询的 {@link EventDispatcher}
 *
 * @author Erzbir
 * @Data: 2024/2/7 03:46
 */
@Slf4j
public class PollingEventDispatcher extends AbstractEventDispatcher implements EventDispatcher {
    // 同步块的锁, 用于控制线程
    private final Object dispatchLock = new Object();
    // 当前是否暂停
    private volatile boolean suspended = false;
    // 事件缓存队列
    private final PriorityBlockingQueue<Event> eventQueue = new PriorityBlockingQueue<>(10, Comparator.comparingInt(Event::getPriority));

    @Override
    protected <E extends Event> void dispatchTo(E event, EventChannel<E> channel) {
        eventQueue.add(event);
        // 如果有事件要分发, 则恢复线程
        if (suspended) {
            resume();
        }
    }

    @Override
    public void start() {
        if (!activated.compareAndSet(false, true)) {
            return;
        }
        Runnable runnable = () -> {
            while (activated.get() && !Thread.currentThread().isInterrupted()) {
                // 如果队列为空则暂让线程等待
                if (eventQueue.isEmpty()) {
                    suspend();
                    continue;
                }
                EventChannelDispatcher<Event> channel = EventChannelDispatcher.INSTANCE;
                Thread.ofVirtual()
                        .name("Dispatcher-Sub-Thread")
                        .start(createTask(channel));
            }
        };
        // 主线程结束后程序不结束, 调用 cancel() 后结束
        new Thread(runnable, "Dispatcher-Thread").start();
    }

    private void resume() {
        synchronized (dispatchLock) {
            dispatchLock.notifyAll();
        }
    }

    private void suspend() {
        try {
            synchronized (dispatchLock) {
                suspended = true;
                dispatchLock.wait();
            }
        } catch (InterruptedException e) {
            log.error("Dispatching error: {}", e.getMessage());
            cancel();
            Thread.currentThread().interrupt();
        }
    }

    private Runnable createTask(EventChannel<Event> channel) {
        return () -> {
            try {
                Event takenEvent = eventQueue.take();
                if (takenEvent instanceof CancelableEvent cancelableEvent) {
                    if (cancelableEvent.isCanceled()) {
                        return;
                    }
                }
                if (!takenEvent.isIntercepted()) {
                    log.debug("Dispatching event: {} to channel: {}", takenEvent, channel.getClass().getSimpleName());
                    channel.broadcast(new DefaultEventContext(takenEvent));
                }
            } catch (Throwable e) {
                log.error("Dispatching to channel: {} error: {}", channel.getClass().getSimpleName(), e.getMessage());
                Thread.currentThread().interrupt();
            }
        };
    }

    @Override
    public void cancel() {
        if (!activated.compareAndSet(true, false)) {
            return;
        }
        eventQueue.clear();
        // 这里需要唤醒等待的线程, 否则线程永远都不会结束
        resume();
    }
}
