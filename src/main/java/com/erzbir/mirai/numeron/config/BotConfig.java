package com.erzbir.mirai.numeron.config;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.utils.BotConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.io.File;
import java.util.List;

/**
 * @author Erzbir
 * @Date: 2022/11/16 21:14
 * <p>
 *
 * </p>
 */
@Configuration
@ComponentScan (basePackages = "com.erzbir.mirai.numeron")
@PropertySource (value = "classpath:application.properties", encoding = "utf-8")
public class BotConfig {

    private static final String deviceInfo = "device.json";
    private static Bot bot;
    @Value ("${master}")
    public long master;
    @Value ("${username}")
    public Long ACCOUNT;
    @Value ("${password}")
    public String PASSWORD;
    @Value ("#{T(java.util.Arrays).stream('${illegalList}'.split(','))}")
    public List<String> illegalList;
    @Value ("#{T(java.util.Arrays).stream('${groupList}'.split(','))}")
    public List<Long> groupList;
    @Value ("#{T(java.util.Arrays).stream('${blackList}'.split(','))}")
    public List<Long> blackList;
    @Value ("#{T(java.util.Arrays).stream('${whiteList}'.split(','))}")
    public List<Long> whiteList;
    public String WORKDIR;

    public static Bot getBot() {
        return bot;
    }


    @Bean
    public Bot bot() {
        GlobalConfig.master = master;
        GlobalConfig.blackList = blackList;
        GlobalConfig.groupList = groupList;
        GlobalConfig.illegalList = illegalList;
        GlobalConfig.whiteList = whiteList;
        WORKDIR = "bots/" + ACCOUNT;
        File file = new File(WORKDIR);
        if (!file.exists()) {
            file.mkdirs();
        }
        file = null;
        bot = BotFactory.INSTANCE.newBot(ACCOUNT, PASSWORD, new BotConfiguration() {
            {
                setWorkingDir(new File(WORKDIR)); // 工作目录
                setHeartbeatStrategy(HeartbeatStrategy.STAT_HB); // 心跳策略
                setProtocol(MiraiProtocol.ANDROID_PAD); // 登陆协议
                fileBasedDeviceInfo(deviceInfo); // 文件保存的名字
            }
        });
        return bot;
    }

}
