package com.erzbir.numeron.message;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Erzbir
 * @Data: 2024/2/7 23:31
 */
@Setter
@Getter
public class MessageMetadata {
    private long fromId;
    private long targetId;
    private long botId;
    private long timestamp;
}
