package com.erzbir.numeron.api.config;

import net.mamoe.mirai.utils.BotConfiguration;

/**
 * @author Erzbir
 * @Date: 2023/4/26 12:22
 */
public class NumeronConfiguration extends BotConfiguration {
    private long master = 0L;    // 主人

    public long getMaster() {
        return master;
    }

    public void setMaster(long master) {
        this.master = master;
    }
}
