package com.erzbir.mirai.numeron.config;

import java.util.HashSet;

/**
 * @author Erzbir
 * @Date: 2022/11/16 21:42
 * <p>这个类只是对BotConfig中成员的一个引用, 可以弃用了<p/>
 */
public class GlobalConfig {
    public static final String OS = System.getProperty("os.name");
    public static final String HOME = System.getenv("HOME");
    public static String  botName;
    public static boolean isOn = true;
    public static Long master;
    public static HashSet<String> illegalList;
    public static HashSet<Long> groupList;
    public static HashSet<Long> blackList;
    public static HashSet<Long> whiteList;

    public static String toStrings() {
        return "\tName: " + botName + "\n\n" +
                "\tOS: " + OS + "\n\n" +
                "\tHOME: " + HOME + "\n\n" +
                "\tMaster: " + master + "\n\n" +
                "\tIllegalList: " + illegalList.toString() + "\n\n" +
                "\tGroupList: " + groupList.toString() + "\n\n" +
                "\tBlackList: " + blackList.toString() + "\n\n" +
                "\tWhiteList: " + whiteList.toString() + "\n\n";
    }
}
