package com.erzbir.numeron.config;

import lombok.Getter;
import lombok.Setter;
import net.mamoe.mirai.utils.BotConfiguration;

/**
 * TODO: remove and refactor
 *
 * @author Erzbir
 * @Date: 2023/5/3 12:47
 */
@Setter
@Getter
public class NumeronBotConfiguration extends BotConfiguration {
    private long master;    // 主人
    private boolean enable;

    public NumeronBotConfiguration(long master) {
        super();
        this.master = master;
        this.enable = false;
    }

    public NumeronBotConfiguration() {
        super();
    }

    public void enable() {
        setEnable(true);
    }

    public void disable() {
        setEnable(false);
    }
}