package com.erzbir.numeron.console.bot;

import com.erzbir.numeron.api.NumeronImpl;
import com.erzbir.numeron.api.bot.BotServiceImpl;
import com.erzbir.numeron.config.NumeronBotConfiguration;
import com.erzbir.numeron.console.NumeronConsole;
import com.erzbir.numeron.exception.LoginConfigReadException;
import com.erzbir.numeron.utils.ConfigCreateUtil;
import com.erzbir.numeron.utils.NumeronLogUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.auth.BotAuthorization;
import net.mamoe.mirai.utils.BotConfiguration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Erzbir
 * @Date: 2023/6/12 10:26
 */
public class BotLoader {
    private static final String config = NumeronImpl.INSTANCE.getConfigDir() + "botconfig.json";

    static {
        try {
            ConfigCreateUtil.createFile(config);
        } catch (IOException e) {
            NumeronLogUtil.logger.error("ERROR", e);
            throw new RuntimeException(e);
        }

    }

    public static List<Bot> load() {
        JsonArray botsJson = null;
        List<Bot> bots = new ArrayList<>();
        BufferedReader fileReader = null;
        try {
            fileReader = new BufferedReader(new FileReader(config));
            JsonElement jsonElement = JsonParser.parseReader(fileReader);
            if (jsonElement != null && jsonElement.isJsonArray()) {
                botsJson = jsonElement.getAsJsonArray();
            } else {
                fileReader.close();
                fileReader = null;
                NumeronConsole.INSTANCE.consoleLogin();
                fileReader = new BufferedReader(new FileReader(config));
                botsJson = JsonParser.parseReader(fileReader).getAsJsonArray();
            }
        } catch (Exception e) {
            LoginConfigReadException exception = new LoginConfigReadException(e);
            NumeronLogUtil.logger.error(exception.getMessage(), exception);
            throw exception;
        } finally {
            if (fileReader != null) {
                try {
                    fileReader.close();
                    fileReader = null;
                } catch (IOException e) {
                    NumeronLogUtil.logger.error(e.getMessage(), e);
                }
            }
        }
        if (botsJson == null) {
            return bots;
        }
        botsJson.forEach(t -> {
            JsonObject jsonObject = t.getAsJsonObject();
            long account = jsonObject.get("account").getAsLong();
            Long master = jsonObject.get("master").getAsLong();
            Boolean enable = jsonObject.get("enable").getAsBoolean();
            String password = jsonObject.get("password").getAsString();
            String loginType = jsonObject.get("loginType").getAsString();
            BotConfiguration.HeartbeatStrategy heartbeatStrategy = BotConfiguration.HeartbeatStrategy.valueOf(jsonObject.get("heartbeatStrategy").getAsString());
            BotConfiguration.MiraiProtocol miraiProtocol = BotConfiguration.MiraiProtocol.valueOf(jsonObject.get("miraiProtocol").getAsString());
            String botDir = NumeronImpl.INSTANCE.getWorkDir() + "bots/" + account + "/";
            ConfigCreateUtil.createDir(botDir);
            NumeronBotConfiguration botConfiguration = new NumeronBotConfiguration() {
                {
                    setMaster(master);
                    setWorkingDir(new File(botDir));
                    setEnable(enable);
                    setHeartbeatStrategy(heartbeatStrategy);
                    setProtocol(miraiProtocol);
                    fileBasedDeviceInfo();
                }
            };
            if (loginType.equals("QR")) {
                BotServiceImpl.INSTANCE.newBot(account, BotAuthorization.byQRCode(), botConfiguration);
            } else {
                BotServiceImpl.INSTANCE.newBot(account, password, botConfiguration);
            }
        });
        return bots;
    }
}
