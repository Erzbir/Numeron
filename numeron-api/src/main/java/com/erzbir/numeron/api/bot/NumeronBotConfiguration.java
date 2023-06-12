package com.erzbir.numeron.api.bot;

import net.mamoe.mirai.utils.BotConfiguration;

/**
 * @author Erzbir
 * @Date: 2023/5/3 12:47
 */
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

    public long getMaster() {
        return master;
    }

    public void setMaster(long master) {
        this.master = master;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public void enable() {
        this.enable = true;
    }

    public void disable() {
        this.enable = false;
    }
}