package com.erzbir.mirai.numeron.configs;

import java.io.Serializable;

/**
 * @author Erzbir
 * @Date: 2022/11/16 21:14
 * <p>
 * 配置类, 机器人的配置
 * </p>
 */
public class NumeronConfig implements Serializable {
    protected String proxy_type;    // 代理类型
    protected String proxy_address;    // 代理地址
    protected String proxy_port;    // 代理端口
    protected String proxy_username;    // 代理用户名
    protected String proxy_password;    // 代理密码

}
