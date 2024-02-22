package com.erzbir.numeron.event;

/**
 * @author Erzbir
 * @Data: 2024/2/7 05:26
 */
public enum StandardListenerResult implements ListenerResult {
    CONTINUE(false, true),
    STOP(false, false),
    TRUNCATED(true, true);


    private final boolean isContinue;
    private final boolean isTruncated;


    StandardListenerResult(boolean isTruncated, boolean isContinue) {
        this.isContinue = isContinue;
        this.isTruncated = isTruncated;
    }

    @Override
    public boolean isTruncated() {
        return isTruncated;
    }

    public boolean isContinue() {
        return isContinue;
    }

}
