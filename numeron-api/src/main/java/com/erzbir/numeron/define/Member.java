package com.erzbir.numeron.define;

import com.erzbir.numeron.action.SendSupport;

/**
 * @author Erzbir
 * @Data: 2024/2/7 19:04
 */
public interface Member extends User, SendSupport {
    String getNickname();
}
