package com.erzbir.numeron.bot;

import java.util.List;

/**
 * @author Erzbir
 * @Data: 2024/2/8 00:42
 */
public interface BotManager extends BotRegistrar {
    List<Bot> getBots();

    default Bot find(long id) {
        try {
            return get(id);
        } catch (NoSuchBotException ignore) {
            return null;
        }
    }

    Bot get(long id);
}
