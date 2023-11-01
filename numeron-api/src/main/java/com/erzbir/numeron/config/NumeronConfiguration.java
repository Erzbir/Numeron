package com.erzbir.numeron.config;

import com.erzbir.numeron.utils.ConfigCreateUtil;
import lombok.Getter;
import lombok.Setter;
import net.mamoe.mirai.Bot;

import java.io.Serializable;
import java.util.List;

/**
 * TODO: refactor
 *
 * @author Erzbir
 * @Date: 2023/4/29 11:49
 */
@Setter
@Getter
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


}
