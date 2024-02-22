package com.erzbir.numeron.message;

import com.erzbir.numeron.action.DeleteSupport;

/**
 * @author Erzbir
 * @Data: 2024/2/7 23:01
 */
public interface MessageContent extends DeleteSupport {
    long getId();

    MessageChain getMessages();

    MessageMetadata getMetadata();
}
