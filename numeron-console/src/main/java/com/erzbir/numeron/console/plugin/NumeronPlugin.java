package com.erzbir.numeron.console.plugin;

/**
 * @author Erzbir
 * @Date: 2023/4/26 14:07
 */
public abstract class NumeronPlugin implements Plugin {
    private final NumeronDescription description;
    private boolean enable = false;

    protected NumeronPlugin(NumeronDescription description) {
        this.description = description;
    }

    @Override
    public NumeronDescription getDescription() {
        return description;
    }

    @Override
    public void enable() {
        enable = true;
        onEnable();
    }

    @Override
    public void disable() {
        enable = false;
        onDisable();
    }

    @Override
    public boolean isEnable() {
        return enable;
    }

}
