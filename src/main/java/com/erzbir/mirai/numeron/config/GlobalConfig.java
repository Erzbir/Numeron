package com.erzbir.mirai.numeron.config;

import java.util.HashSet;

/**
 * @author Erzbir
 * @Date: 2022/11/16 21:42
 * <p>这个类只是对BotConfig的一个引用, 可以启用了<p/>
 */
public class GlobalConfig {
    public static final String OS = System.getProperty("os.name");
    public static final String HOME = System.getenv("HOME");
    public static boolean isOn = true;
    public static Long master;
    public static HashSet<String> illegalList;
    public static HashSet<Long> groupList;
    public static HashSet<Long> blackList;
    public static HashSet<Long> whiteList;

    public static String toStrings() {
        return "\tOS: " + OS + "\n\n" +
                "\tHOME: " + HOME + "\n\n" +
                "\tmaster: " + master + "\n\n" +
                "\tillegalList: " + illegalList.toString() + "\n\n" +
                "\tgroupList: " + groupList.toString() + "\n\n" +
                "\tblackList: " + blackList.toString() + "\n\n" +
                "\twhiteList: " + whiteList.toString() + "\n\n";
    }
}
