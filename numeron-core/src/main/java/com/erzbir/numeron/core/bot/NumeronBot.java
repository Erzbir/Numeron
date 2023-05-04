package com.erzbir.numeron.core.bot;

import com.erzbir.numeron.api.bot.AbstractNumeronBot;
import com.erzbir.numeron.core.utils.CoroutineScopeBridge;
import com.erzbir.numeron.core.context.ListenerContext;
import com.erzbir.numeron.core.processor.MessageAnnotationProcessor;
import com.erzbir.numeron.core.utils.FixProtocol;
import com.erzbir.numeron.utils.ConfigCreateUtil;
import com.erzbir.numeron.utils.ConfigWriteException;
import com.erzbir.numeron.utils.JsonUtil;
import com.erzbir.numeron.utils.NumeronLogUtil;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.events.BotEvent;
import net.mamoe.mirai.utils.BotConfiguration;

import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

/**
 * @author Erzbir
 * @Date: 2022/12/28 00:20
 */
public class NumeronBot extends AbstractNumeronBot implements Serializable {
    public static final NumeronBot INSTANCE = new NumeronBot();
    private final transient String deviceInfo = "device.json";
    private long master = 0L;    // 主人
    private long account = 0L;   // 帐号
    private String password = "";    // 密码
    private transient String workDir = "erzbirnumeron/";  // 文件存储目录
    private boolean enable = true;
    private transient Bot bot;
    private BotConfiguration.HeartbeatStrategy heartbeatStrategy = BotConfiguration.HeartbeatStrategy.STAT_HB;
    private BotConfiguration.MiraiProtocol miraiProtocol = BotConfiguration.MiraiProtocol.ANDROID_PAD;

    private NumeronBot() {
        init();
    }

    public NumeronBot(long account, String password,
                      long master, String workDir,
                      BotConfiguration.HeartbeatStrategy heartbeatStrategy,
                      BotConfiguration.MiraiProtocol miraiProtocol) {
        this.account = account;
        this.password = password;
        this.workDir = workDir;
        this.heartbeatStrategy = heartbeatStrategy;
        this.master = master;
        this.miraiProtocol = miraiProtocol;
        this.bot = BotFactory.INSTANCE.newBot(account, password, new BotConfiguration() {
            {
                setWorkingDir(new File(workDir + "bots/" + account + "/")); // 工作目录
                setHeartbeatStrategy(heartbeatStrategy); // 心跳策略
                setProtocol(miraiProtocol); // 登陆协议
                fileBasedDeviceInfo(deviceInfo); // 文件保存的名字
            }
        });
    }

    public NumeronBot(long account, String password, long master, String workDir, BotConfiguration botConfiguration) {
        this.account = account;
        this.master = master;
        this.password = password;
        this.workDir = workDir;
        this.bot = BotFactory.INSTANCE.newBot(account, password, botConfiguration);
    }

    public NumeronBot(long account, String password, long master, String workDir) {
        this.master = master;
        this.account = account;
        this.password = password;
        this.workDir = workDir;
        this.bot = BotFactory.INSTANCE.newBot(account, password, new BotConfiguration() {
            {
                setWorkingDir(new File(workDir + "bots/" + account + "/")); // 工作目录
                setHeartbeatStrategy(heartbeatStrategy); // 心跳策略
                setProtocol(miraiProtocol); // 登陆协议
                fileBasedDeviceInfo(deviceInfo); // 文件保存的名字
            }
        });
    }


    private void init() {
        Scanner scanner = new Scanner(System.in);
        String configFile = this.workDir + "config/botconfig.json";
        try {
            ConfigCreateUtil.createFile(configFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (load(new File(configFile))) {
            bot = createBot();
            return;
        }
        if (account == 0) {
            NumeronLogUtil.err("帐号为空");
            while (account == 0) {
                System.out.print("输入帐号: ");
                try {
                    account = scanner.nextLong();
                    scanner.nextLine();
                } catch (IllegalArgumentException e) {
                    NumeronLogUtil.err("请输入数字帐号!");
                }
            }
        }
        if (password == null || password.isEmpty()) {
            NumeronLogUtil.err("密码为空");
            while (password.isEmpty()) {
                System.out.print("输入密码: ");
                password = scanner.next();
            }
        }
        if (master == 0) {
            NumeronLogUtil.err("主人未设置");
            while (master == 0) {
                System.out.print("输入主人帐号: ");
                try {
                    master = scanner.nextLong();
                } catch (IllegalArgumentException e) {
                    NumeronLogUtil.err("请输入数字帐号!");
                }
            }
        }
        scanner.close();
        if (notVerify()) {
            throw new RuntimeException();
        }
        ConfigCreateUtil.createDir(workDir + "bots/" + account + "/");
        bot = createBot();
        save();
    }

    private boolean notVerify() {
        return master == 0L || password == null || account == 0L;
    }

    private void save() {
        NumeronLogUtil.info("配置成功, 将保存配置....");
        new Thread(() -> {
            NumeronLogUtil.info("开始保存配置......");
            String configFile = workDir + "config/botconfig.json";
            HashMap<String, NumeronBot> hashMap = new HashMap<>();
            hashMap.put("bot", this);
            try {
                JsonUtil.dump(configFile, hashMap, HashMap.class);
            } catch (ConfigWriteException e) {
                throw new RuntimeException(e);
            }
            NumeronLogUtil.info("保存成功\n");
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
        String s = workDir + "bots/" + account + "/";
        ConfigCreateUtil.createDir(s);
        FixProtocol.INSTANCE.fix();
        return BotFactory.INSTANCE.newBot(account, password, new BotConfiguration() {
            {
                setWorkingDir(new File(s)); // 工作目录
                setHeartbeatStrategy(heartbeatStrategy); // 心跳策略
                setProtocol(miraiProtocol); // 登陆协议
                fileBasedDeviceInfo(deviceInfo); // 文件保存的名字
            }
        });
    }

    public void login() {
        bot.login();
    }

    public long getAccount() {
        return account;
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

    @Override
    public String getWorkDir() {
        return this.workDir;
    }

    public void setWorkDir(String dir) {
        this.workDir = dir;
    }

    public EventChannel<BotEvent> getEventChannel() {
        return bot.getEventChannel();
    }

    @Override
    public void launch() {
        setEnable(true);
        // 重新注册监听
        new MessageAnnotationProcessor().onApplicationEvent();
    }

    @Override
    public void shutdown() {
        ListenerContext.INSTANCE.cancelAll();
        CoroutineScopeBridge.Companion.cancel(this);
        setEnable(false);
    }
}
