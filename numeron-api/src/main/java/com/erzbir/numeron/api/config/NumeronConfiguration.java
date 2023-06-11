package com.erzbir.numeron.api.config;

import com.erzbir.numeron.utils.ConfigCreateUtil;

/**
 * @author Erzbir
 * @Date: 2023/4/29 11:49
 */
public class NumeronConfiguration {
    public static final NumeronConfiguration INSTANCE = new NumeronConfiguration();
    private String workDir;
    private String cacheDir;
    private String pluginWorkDir;
    private Boolean cache;

    private NumeronConfiguration() {
        workDir = "erzbirnumeron/";
        cacheDir = workDir + "cache/";
        pluginWorkDir = workDir + "plugin/";
        cache = false;
        ConfigCreateUtil.createDir(workDir);
        ConfigCreateUtil.createDir(cacheDir);
        ConfigCreateUtil.createDir(pluginWorkDir);
    }

    public String getWorkDir() {
        return workDir;
    }

    public void setWorkDir(String workDir) {
        this.workDir = workDir;
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
}
