package com.erzbir.numeron.event;

/**
 * 监听完成返回的结果
 *
 * @author Erzbir
 * @Data: 2024/2/3 11:44
 */
public interface ListenerResult {
    boolean isTruncated();

    boolean isContinue();
}
