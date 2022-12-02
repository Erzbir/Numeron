package com.erzbir.mirai.numeron.job;

import cn.hutool.cron.pattern.CronPattern;
import cn.hutool.cron.task.CronTask;
import cn.hutool.cron.task.Task;

/**
 * @author Erzbir
 * @Date: 2022/12/2 10:44
 */
public class TimeJob implements Task {
    private final String id;
    private TimeAction action;

    private TimeJob(String id) {
        this.id = id;
    }

    public static CronTask createTask(String id, String cron) {
        return new CronTask(id, CronPattern.of(cron), new TimeJob(id));
    }

    public static CronTask createTask(String id, String cron, TimeAction action) {
        return new CronTask(id, CronPattern.of(cron), new TimeJob(id).setAction(action));
    }

    public static void main(String[] args) {
    }

    public TimeJob setAction(TimeAction action) {
        this.action = action;
        this.action.setId(id);
        return this;
    }

    @Override
    public void execute() {
        if (action != null) {
            action.run();
        }
    }
}
