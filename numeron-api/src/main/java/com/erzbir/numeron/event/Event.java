package com.erzbir.numeron.event;

/**
 * @author Erzbir
 * @Data: 2023/12/6 10:46
 */
public interface Event {
    /**
     * @return 事件产生的时间
     */
    long timestamp();

    /**
     * @return 发布事件的主体
     */
    Object getSource();

    /**
     * @return 是否被拦截
     */
    boolean isIntercepted();

    /**
     * 拦截这个事件. 当事件被拦截之后, 调度器就不会再将事件分发到事件频道
     * <p>
     * 如果一个事件拦截器返回的结果是 false, 那么将会调用此方法拦截
     */
    void intercepted();

    /**
     * @return 优先级
     */
    int getPriority();
}
