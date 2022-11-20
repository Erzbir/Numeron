package com.erzbir.mirai.numeron.config;

import java.util.HashSet;

/**
 * @author Erzbir
 * @Date: 2022/11/16 21:42
 */

public class GlobalConfig {
    public static String OS = System.getProperty("os.name");
    public static String HOME = System.getenv("HOME");
    public static boolean isOn = true;
    public static Long master;
    public static HashSet<String> illegalList;
    public static HashSet<Long> groupList;
    public static HashSet<Long> blackList;
    public static HashSet<Long> whiteList;

    public static String toStrings() {
        return "OS: " + OS + "\n" +
                "\tHOME: " + HOME + "\n" +
                "\tmaster: " + master + "\n" +
                "\tillegalList: " + illegalList.toString() + "\n" +
                "\tgroupList: " + groupList.toString() + "\n" +
                "\tblackList: " + blackList.toString() + "\n" +
                "\twhiteList: " + whiteList.toString() + "\n";
    }
}
