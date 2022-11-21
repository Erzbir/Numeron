package com.erzbir.mirai.numeron.config;

import com.erzbir.mirai.numeron.annotation.sql.DataValue;
import com.erzbir.mirai.numeron.sql.SqlUtil;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.utils.BotConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

/**
 * @author Erzbir
 * @Date: 2022/11/16 21:14
 * <p>
 * 配置类, 数据库资源的加载用反射和数据读操作实现
 * </p>
 */
@Configuration
@Slf4j
@ComponentScan (basePackages = "com.erzbir.mirai.numeron")
//@PropertySource (value = "classpath:application.properties", encoding = "utf-8")
public class BotConfig {
    private static final String deviceInfo = "device.json";
    public static Long master;
    public static Long account;
    public static String password;
    //@Value ("#{T(java.util.HashSet).addAll(T(java.util.Arrays).stream('${illegalList}'.split(',')))}")
    @DataValue
    public static HashSet<String> illegalList;
    //@Value ("#{T(java.util.HashSet).addAll(T(java.util.Arrays).stream('${groupList}'.split(',')))}")
    @DataValue
    public static HashSet<Long> groupList;
    //@Value ("#{T(java.util.HashSet).addAll(T(java.util.Arrays).stream('${blackList}'.split(',')))}")
    @DataValue
    public static HashSet<Long> blackList;
    //@Value ("#{T(java.util.HashSet).addAll(T(java.util.Arrays).stream('${whiteList}'.split(',')))}")
    @DataValue
    public static HashSet<Long> whiteList;
    private static Bot bot;
    private static String WORKDIR;
    private static BotConfiguration.HeartbeatStrategy heartbeatStrategy = BotConfiguration.HeartbeatStrategy.STAT_HB;
    private static BotConfiguration.MiraiProtocol miraiProtocol = BotConfiguration.MiraiProtocol.ANDROID_PAD;

    static {
        init();
        dbInit();
    }

    public static Bot getBot() {
        return bot;
    }

    private static void init() {
        FileInputStream fileInputStream = null;
        Properties properties = new Properties();
        try {
            fileInputStream = new FileInputStream("config/BotConfig.properties");
            properties.load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        log.info("加载机器人配置");
        if (!properties.isEmpty() && properties.getProperty("enable").equals("true")) {
            master = Long.parseLong(properties.getProperty("master"));
            account = Long.parseLong(properties.getProperty("account"));
            password = properties.getProperty("password");
            heartbeatStrategy = BotConfiguration.HeartbeatStrategy.valueOf((properties.getProperty("heartbeatStrategy")));
            miraiProtocol = BotConfiguration.MiraiProtocol.valueOf((properties).getProperty("protocol"));
            return;
        } else {
            log.info("帐号配置为空或者未启用, 将手动输入");
        }
        Scanner scan = new Scanner(System.in);
        while (account == null) {
            try {
                System.out.print("输入帐号: ");
                account = scan.nextLong();
            } catch (IllegalArgumentException e) {
                System.out.println("请输入数字帐号!!!!");
            }
        }
        while (password == null) {
            System.out.print("输入密码: ");
            password = scan.nextLine();
        }
        while (master == null) {
            System.out.print("输入主人QQ: ");
            master = scan.nextLong();
        }
        scan.close();
        log.info("配置成功, 将保存配置....");
        save();
    }

    private static void dbInit() {
        log.info("开始载入数据库数据");
        List.of(BotConfig.class.getDeclaredFields()).forEach(field -> {
            DataValue annotation = field.getDeclaredAnnotation(DataValue.class);
            if (annotation != null) {
                try {
                    field.set(null, SqlUtil.perms.get(field.getName()));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

            }
        });
        log.info("违禁词列表: " + illegalList.toString());
        log.info("启用群列表: " + groupList.toString());
        log.info("黑名单列表: " + blackList.toString());
        log.info("白名单列表: " + whiteList.toString());
    }

    private static void save() {
        log.info("开始保存配置......");
        FileOutputStream outputStream = null;
        Properties properties;
        File file = new File("config");
        if (!file.exists()) {
            if (!file.mkdir()) {
                return;
            }
        }
        try {
            outputStream = new FileOutputStream("config/BotConfig.properties");
            properties = new Properties();
            properties.setProperty("master", String.valueOf(master));
            properties.setProperty("account", String.valueOf(account));
            properties.setProperty("password", password);
            properties.setProperty("protocol", miraiProtocol.name());
            properties.setProperty("heartbeatStrategy", heartbeatStrategy.name());
            properties.setProperty("enable", "true");
            properties.store(outputStream, null);
            log.info("保存成功");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Bean
    public Bot bot() {
        GlobalConfig.master = master;
        GlobalConfig.blackList = blackList;
        GlobalConfig.groupList = groupList;
        GlobalConfig.illegalList = illegalList;
        GlobalConfig.whiteList = whiteList;
        WORKDIR = "bots/" + account;
        File file = new File(WORKDIR);
        if (!file.exists()) {
            if (file.mkdirs()) {
                return bot;
            }
        }
        bot = BotFactory.INSTANCE.newBot(account, password, new BotConfiguration() {
            {
                setWorkingDir(new File(WORKDIR)); // 工作目录
                setHeartbeatStrategy(heartbeatStrategy); // 心跳策略
                setProtocol(miraiProtocol); // 登陆协议
                fileBasedDeviceInfo(deviceInfo); // 文件保存的名字
            }
        });
        return bot;
    }
}
