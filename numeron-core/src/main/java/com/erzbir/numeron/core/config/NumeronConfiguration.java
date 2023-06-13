package com.erzbir.numeron.core.config;

import com.erzbir.numeron.utils.ConfigCreateUtil;
import net.mamoe.mirai.Bot;

import java.io.Serializable;
import java.util.List;

/**
 * @author Erzbir
 * @Date: 2023/4/29 11:49
 */
public class NumeronConfiguration implements Serializable {
    public static final NumeronConfiguration INSTANCE = new NumeronConfiguration();
    private String workDir;
    private String cacheDir;
    private String pluginWorkDir;
    private String configDir;
    private Boolean cache;
    private List<Bot> bots;

    private NumeronConfiguration() {
        workDir = "erzbirnumeron/";
        cacheDir = workDir + "cache/";
        pluginWorkDir = workDir + "plugin/";
        configDir = workDir + "config/";
        cache = false;
        ConfigCreateUtil.createDir(workDir);
        ConfigCreateUtil.createDir(cacheDir);
        ConfigCreateUtil.createDir(pluginWorkDir);
        ConfigCreateUtil.createDir(configDir);
    }

    public String getWorkDir() {
        return workDir;
    }

    public void setWorkDir(String workDir) {
        this.workDir = workDir;
    }

    public String getConfigDir() {
        return configDir;
    }

    public void setConfigDir(String configDir) {
        this.configDir = configDir;
    }


    public String getCacheDir() {
        return cacheDir;
    }

    public void setCacheDir(String cacheDir) {
        this.cacheDir = cacheDir;
    }

    public String getPluginWorkDir() {
        return pluginWorkDir;
    }

    public void setPluginWorkDir(String pluginWorkDir) {
        this.pluginWorkDir = pluginWorkDir;
    }


    public Boolean getCache() {
        return cache;
    }

    public void setCache(Boolean cache) {
        this.cache = cache;
    }

    public List<Bot> getBots() {
        return bots;
    }

    public void setBots(List<Bot> bots) {
        this.bots = bots;
    }
}
