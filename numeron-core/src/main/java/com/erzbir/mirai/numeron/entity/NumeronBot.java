package com.erzbir.mirai.numeron.entity;

import net.mamoe.mirai.Bot;

/**
 * @author Erzbir
 * @Date: 2022/12/28 00:20
 */
public class NumeronBot {
    public static final NumeronBot INSTANCE = new NumeronBot();
    private long master = 0;
    private boolean open = true;
    private Bot bot;

    private NumeronBot() {

    }

    public long getMaster() {
        return master;
    }

    public void setMaster(long master) {
        this.master = master;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public Bot getBot() {
        return bot;
    }

    public void setBot(Bot bot) {
        this.bot = bot;
    }

    public void turnOn() {
        setOpen(true);
    }

    public void turnOff() {
        setOpen(false);
    }
}
