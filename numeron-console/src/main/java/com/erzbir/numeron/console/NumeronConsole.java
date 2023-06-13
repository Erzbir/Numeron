package com.erzbir.numeron.console;

import com.erzbir.numeron.api.NumeronImpl;
import com.erzbir.numeron.api.bot.NumeronBotConfiguration;
import com.erzbir.numeron.utils.ConfigCreateUtil;
import com.erzbir.numeron.utils.NumeronLogUtil;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.mamoe.mirai.utils.BotConfiguration;

import java.io.*;
import java.util.Scanner;

/**
 * @author Erzbir
 * @Date: 2023/6/12 01:49
 */
public class NumeronConsole {
    public static final NumeronConsole INSTANCE = new NumeronConsole();

    private NumeronConsole() {

    }

    public void consoleLogin() {
        Scanner scanner = new Scanner(System.in);
        String configFile = NumeronImpl.INSTANCE.getConfigDir() + "botconfig.json";
        try {
            ConfigCreateUtil.createFile(configFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        long account = 0, master = 0;
        String password = "";
        while (account == 0) {
            System.out.print("输入帐号: ");
            try {
                account = scanner.nextLong();
                scanner.nextLine();
            } catch (IllegalArgumentException e) {
                NumeronLogUtil.err("请输入数字帐号!");
            }
        }
        while (password.isEmpty()) {
            System.out.print("输入密码: ");
            password = scanner.next();
        }
        while (master == 0) {
            System.out.print("输入主人帐号: ");
            try {
                master = scanner.nextLong();
            } catch (IllegalArgumentException e) {
                NumeronLogUtil.err("请输入数字帐号!");
            }
        }
        scanner.close();
        String botDir = NumeronImpl.INSTANCE.getWorkDir() + "bots/" + account + "/";
        ConfigCreateUtil.createDir(botDir);
        NumeronBotConfiguration botConfiguration = new NumeronBotConfiguration();
        botConfiguration.setWorkingDir(new File(botDir));
        botConfiguration.setEnable(true);
        botConfiguration.setMaster(master);
        botConfiguration.setProtocol(BotConfiguration.MiraiProtocol.ANDROID_WATCH);
        botConfiguration.fileBasedDeviceInfo();
        JsonArray botsJson = null;
        try (BufferedReader fileReader = new BufferedReader(new FileReader(configFile))) {
            botsJson = JsonParser.parseReader(fileReader).getAsJsonArray();
        } catch (Exception e) {
            NumeronLogUtil.logger.error(e);
        }
        if (botsJson == null) {
            botsJson = new JsonArray();
        }
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("account", account);
        jsonObject.addProperty("password", password);
        jsonObject.addProperty("master", master);
        jsonObject.addProperty("enable", true);
        jsonObject.addProperty("heartbeatStrategy", botConfiguration.getHeartbeatStrategy().toString());
        jsonObject.addProperty("miraiProtocol", botConfiguration.getProtocol().toString());
        jsonObject.addProperty("loginType", "QR");
        botsJson.add(jsonObject);
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(configFile))) {
            Gson gson = new Gson();
            gson.toJson(botsJson, bufferedWriter);
        } catch (Exception e) {
            NumeronLogUtil.logger.error(e);
        }
    }
}
