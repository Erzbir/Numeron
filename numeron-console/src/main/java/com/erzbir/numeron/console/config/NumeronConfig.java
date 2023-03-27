package com.erzbir.numeron.console.config;


import net.mamoe.mirai.utils.BotConfiguration;

import java.io.Serializable;

/**
 * @author Erzbir
 * @Date: 2022/11/16 21:14
 * <p>
 * 配置类, 机器人的配置
 * </p>
 */
public class NumeronConfig extends BotConfiguration implements Serializable {
    private long master;
    private long account;
    private long password;
    private String folder;
}
