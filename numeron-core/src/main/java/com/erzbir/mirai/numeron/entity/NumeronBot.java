package com.erzbir.mirai.numeron.entity;

import com.erzbir.mirai.numeron.utils.ConfigCreateUtil;
import com.erzbir.mirai.numeron.utils.JsonUtil;
import com.erzbir.mirai.numeron.utils.MiraiLogUtil;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.utils.BotConfiguration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Serializable;
import java.util.Scanner;

/**
 * @author Erzbir
 * @Date: 2022/12/28 00:20
 */
public class NumeronBot implements Serializable {
    public static NumeronBot INSTANCE = new NumeronBot();
    private final transient String deviceInfo = "device.json";
    private long master = 0L;    // 主人
    private long account = 0L;   // 帐号
    private String password = "";    // 密码
    private transient String folder = "erzbirnumeron/";  // 文件存储目录
    private boolean enable = true;
    private transient Bot bot;
    private BotConfiguration.HeartbeatStrategy heartbeatStrategy = BotConfiguration.HeartbeatStrategy.STAT_HB;
    private BotConfiguration.MiraiProtocol miraiProtocol = BotConfiguration.MiraiProtocol.ANDROID_PAD;

    private NumeronBot() {
        init();
    }

    public void init() {
        Scanner scanner = new Scanner(System.in);
        String configFile = this.folder + "config/botconfig.json";
        ConfigCreateUtil.createFile(configFile);
        if (load(new File(configFile))) {
            return;
        }
        if (account == 0) {
            MiraiLogUtil.err("帐号为空");
            while (account == 0) {
                System.out.print("输入帐号: ");
                try {
                    account = scanner.nextLong();
                    scanner.nextLine();
                } catch (IllegalArgumentException e) {
                    MiraiLogUtil.err("请输入数字帐号!");
                }
            }
        }
        if (password == null || password.isEmpty()) {
            MiraiLogUtil.err("密码为空");
            while (password.isEmpty()) {
                System.out.print("输入密码: ");
                password = scanner.next();
            }
        }
        if (master == 0) {
            MiraiLogUtil.err("主人未设置");
            while (master == 0) {
                System.out.print("输入主人帐号: ");
                try {
                    master = scanner.nextLong();
                } catch (IllegalArgumentException e) {
                    MiraiLogUtil.err("请输入数字帐号!");
                }
            }
        }
        scanner.close();
        if (notVerify()) {
            return;
        }
        ConfigCreateUtil.createDir(folder + "bots/" + account + "/");
        bot = createBot();
        save();
    }

    private boolean notVerify() {
        return master == 0L || password == null || account == 0L;
    }

    private void save() {
        MiraiLogUtil.info("配置成功, 将保存配置....");
        new Thread(() -> {
            MiraiLogUtil.info("开始保存配置......");
            String configFile = folder + "config/botconfig.json";
            JsonUtil.dump(configFile, this, this.getClass());
            MiraiLogUtil.info("保存成功\n");
        }).start();
    }

    private boolean load(File file) {
        JsonObject bot1;
        try (BufferedReader fileReader = new BufferedReader(new FileReader(file))) {
            bot1 = JsonParser.parseReader(fileReader).getAsJsonObject().getAsJsonObject("bot");
        } catch (Exception e) {
            return false;
        }
        this.account = bot1.get("account").getAsLong();
        this.master = bot1.get("master").getAsLong();
        this.enable = bot1.get("enable").getAsBoolean();
        this.password = bot1.get("password").getAsString();
        this.heartbeatStrategy = BotConfiguration.HeartbeatStrategy.valueOf(bot1.get("heartbeatStrategy").getAsString());
        this.miraiProtocol = BotConfiguration.MiraiProtocol.valueOf(bot1.get("miraiProtocol").getAsString());
        return !notVerify();
    }

    private Bot createBot() {
        return BotFactory.INSTANCE.newBot(account, password, new BotConfiguration() {
            {
                setWorkingDir(new File(folder + "bots/" + account + "/")); // 工作目录
                setHeartbeatStrategy(heartbeatStrategy); // 心跳策略
                setProtocol(miraiProtocol); // 登陆协议
                fileBasedDeviceInfo(deviceInfo); // 文件保存的名字
            }
        });
    }

    public long getMaster() {
        return master;
    }

    public void setMaster(long master) {
        this.master = master;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public Bot getBot() {
        return bot;
    }

    public void setBot(Bot bot) {
        this.bot = bot;
    }

    public String getFolder() {
        return this.folder;
    }

    public void setFolder(String dir) {
        this.folder = dir;
    }

    public void turnOn() {
        setEnable(true);
    }

    public void turnOff() {
        setEnable(false);
    }
}
