package com.erzbir.numeron.console.plugin;

/**
 * @author Erzbir
 * @Date: 2023/4/26 14:07
 */
public abstract class JavaPlugin implements Plugin {
    protected final PluginDescription description;
    protected boolean enable = false;
    protected PluginContext pluginContext;

    public JavaPlugin(PluginDescription description) {
        this.description = description;
    }

    @Override
    public PluginDescription getDescription() {
        return description;
    }

    @Override
    public PluginContext getPluginContext() {
        return pluginContext;
    }

    @Override
    public void setPluginContext(PluginContext pluginContext) {
        this.pluginContext = pluginContext;
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
