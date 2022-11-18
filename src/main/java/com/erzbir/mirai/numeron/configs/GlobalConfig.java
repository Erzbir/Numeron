package com.erzbir.mirai.numeron.configs;

import java.util.List;

/**
 * @author Erzbir
 * @Date: 2022/11/16 21:42
 */

public class GlobalConfig {
    public static String OS = System.getProperty("os.name");
    public static String HOME = System.getenv("HOME");
    public static boolean isOn = true;
    public static long master;
    public static List<String> illegalList;
    public static List<Long> groupList;
    public static List<Long> blackList;
    public static List<Long> whiteList;

    public static String toStrings() {
        return "GlobalConfig{" +
                "master: " + master + "\n" +
                "\tillegalList: " + illegalList.toString() + "\n" +
                "\tgroupList: " + groupList.toString() + "\n" +
                "\tblackList: " + blackList.toString() + "\n" +
                "\twhiteList: " + whiteList.toString() + "\n" +
                "\tOS: " + OS + "\n" +
                "\tHOME: " + HOME + "\n" +
                "}";
    }
}
