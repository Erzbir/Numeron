package com.erzbir.numeron.console.bot;

import com.erzbir.numeron.api.NumeronImpl;
import com.erzbir.numeron.api.bot.BotServiceImpl;
import com.erzbir.numeron.api.bot.NumeronBotConfiguration;
import com.erzbir.numeron.utils.NumeronLogUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.auth.BotAuthorization;
import net.mamoe.mirai.utils.BotConfiguration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Erzbir
 * @Date: 2023/6/12 10:26
 */
public class BotLoader {
    private static final String config = "botconfig.json";

    public static List<Bot> load() {
        JsonArray botsJson = null;
        List<Bot> bots = new ArrayList<>();
        try (BufferedReader fileReader = new BufferedReader(new FileReader(NumeronImpl.INSTANCE.getConfigDir() + config))) {
            botsJson = JsonParser.parseReader(fileReader).getAsJsonArray();
        } catch (Exception e) {
            e.printStackTrace();
            NumeronLogUtil.logger.error(e);
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
            String loginType = jsonObject.get("login_type").getAsString();
            BotConfiguration.HeartbeatStrategy heartbeatStrategy = BotConfiguration.HeartbeatStrategy.valueOf(jsonObject.get("heartbeatStrategy").getAsString());
            BotConfiguration.MiraiProtocol miraiProtocol = BotConfiguration.MiraiProtocol.valueOf(jsonObject.get("miraiProtocol").getAsString());
            NumeronBotConfiguration botConfiguration = new NumeronBotConfiguration() {
                {
                    setMaster(master);
                    setWorkingDir(new File(NumeronImpl.INSTANCE.getWorkDir() + "bots/" + account));
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
