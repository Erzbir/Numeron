package com.erzbir.numeron.event.bot;

import com.erzbir.numeron.bot.Bot;
import com.erzbir.numeron.event.Event;

/**
 * @author Erzbir
 * @Data: 2024/2/7 22:56
 */
public interface BotEvent extends Event {
    Bot getBot();
}
