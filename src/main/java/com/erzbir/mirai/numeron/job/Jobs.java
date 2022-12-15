package com.erzbir.mirai.numeron.job;

import cn.hutool.cron.CronUtil;
import com.erzbir.mirai.numeron.job.inter.TimeTask;

import java.util.HashMap;

/**
 * @author Erzbir
 * @Date: 2022/12/5 17:55
 * 定时任务全部放到这个包装类
 */
public class Jobs {
    public static Jobs INSTANCE = new Jobs();
    private final HashMap<String, TimeTask> jobSet = new HashMap<>();

    private Jobs() {
        CronUtil.setMatchSecond(true);
    }

    public void add(TimeTask task) {
        String id = task.getId() + task.getClass();
        jobSet.put(id, task);
        if (CronUtil.getScheduler().isStarted()) {
            CronUtil.stop();
        }
        jobSet.forEach((k, v) -> CronUtil.schedule(k, v.getCron(), v));
        CronUtil.start();
    }

    public void remove(String id, Class<? extends TimeTask> jobType) {
        jobSet.remove(id + jobType);
        CronUtil.remove(id + jobType);
    }

    public boolean exist(TimeTask task) {
        return jobSet.containsKey(task.getId() + task.getClass());
    }

    public void clean() {
        jobSet.forEach((k, v) -> CronUtil.remove(k));
    }

    public String getString() {
        return CronUtil.getScheduler().toString();
    }
}

