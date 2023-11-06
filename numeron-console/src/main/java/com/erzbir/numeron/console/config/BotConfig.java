package com.erzbir.numeron.console.config;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Erzbir
 * @Date 2023/11/6
 */
@Data
public class BotConfig implements Serializable {
    private Long account;
    private String password;
    private Boolean enable;
    private Long master;
    private String heartbeatStrategy;
    private String miraiProtocol;
    private String loginType;
}
