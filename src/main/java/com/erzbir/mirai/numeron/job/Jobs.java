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
        System.out.println(task.getTask().getClass().getName());
        HashMap<String, TimeJob> stringTimeJobHashMap = JOBS.computeIfAbsent(task.getTask().getClass().getName(), k -> new HashMap<>());
        stringTimeJobHashMap.put(task.getName(), task);
        System.out.println(task.getName());
        task.start();
    }


    public static void remove(Class<? extends TimeAction> id, String name) {
        System.out.println(id.getName());
        JOBS.get(id.getName()).get(name).stop();
        JOBS.get(id.getName()).remove(name);
    }

    public static TimeJob get(Class<? extends TimeAction> id, String name) {
        return JOBS.get(id.getName()).get(name);
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

