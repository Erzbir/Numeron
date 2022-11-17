package com.erzbir.mirai.numeron.configs;

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
 * @Author: Erzbir
 * @Date: 2022/11/16 21:14
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
    public String WORKDIR = "bots/" + ACCOUNT;

    public static Bot getBot() {
        return bot;
    }

    //设备认证信息文件

    @Bean
    public Bot bot() {
        GlobalConfig.master = master;
        GlobalConfig.blackList = blackList;
        GlobalConfig.groupList = groupList;
        GlobalConfig.illegalList = illegalList;
        GlobalConfig.whiteList = whiteList;
        File file = new File(WORKDIR);
        if (!file.exists()) {
            file.mkdirs();
        }
        bot = BotFactory.INSTANCE.newBot(ACCOUNT, PASSWORD, new BotConfiguration() {
            {
                setWorkingDir(new File(WORKDIR));
                setHeartbeatStrategy(HeartbeatStrategy.STAT_HB);
                setProtocol(MiraiProtocol.ANDROID_PAD); // 切换协议
                //保存设备信息到文件deviceInfo.json文件里相当于是个设备认证信息
                fileBasedDeviceInfo(deviceInfo);
            }
        });
        return bot;
    }

}
