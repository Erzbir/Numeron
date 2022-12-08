package com.erzbir.mirai.numeron.job;

import com.erzbir.mirai.numeron.job.inter.TimeAction;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Erzbir
 * @Date: 2022/12/5 17:55
 * 定时任务全部放到这个包装类
 */
public class Jobs {
    private static final HashMap<String, HashMap<String, TimeJob>> JOBS = new HashMap<>();

    public static void add(TimeJob task) {
        HashMap<String, TimeJob> stringTimeJobHashMap = JOBS.computeIfAbsent(task.getTask().getClass().getName(), k -> new HashMap<>());
        Class<? extends TimeAction> aClass = task.getTask().getClass();
        String name = task.getName();
        if (exist(aClass, name)) {
            remove(aClass, name);
        }
        stringTimeJobHashMap.put(name, task);
        task.start();
    }


    public static void remove(Class<? extends TimeAction> id, String name) {
        JOBS.get(id.getName()).get(name).stop();
        JOBS.get(id.getName()).remove(name);
    }

    public static TimeJob get(Class<? extends TimeAction> id, String name) {
        return JOBS.get(id.getName()).get(name);
    }

    private static boolean exist(Class<? extends TimeAction> id, String name) {
        return JOBS.get(id.getName()).get(name) != null;
    }


    public static void clean() {
        JOBS.forEach((k, v) -> v.forEach((h, o) -> o.stop(true)));
    }

    public static String getString() {
        StringBuilder ret = new StringBuilder();
        JOBS.forEach((k, v) -> {
            ret.append(k);
            ret.append("{");
            Set<String> keySet = v.keySet();
            Iterator<String> iterator = keySet.iterator();
            while (iterator.hasNext()) {
                ret.append(iterator.next());
                if (iterator.hasNext()) {
                    ret.append(", ");
                }
            }
            ret.append("}");
        });
        return ret.toString();
    }
}

