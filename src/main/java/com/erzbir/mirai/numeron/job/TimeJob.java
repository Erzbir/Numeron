package com.erzbir.mirai.numeron.job;

import cn.hutool.cron.Scheduler;
import cn.hutool.cron.pattern.CronPattern;
import com.erzbir.mirai.numeron.job.inter.STask;
import com.erzbir.mirai.numeron.job.inter.TimeAction;
import lombok.Getter;
import lombok.Setter;
import net.mamoe.mirai.contact.Contact;

import java.util.UUID;

/**
 * @author Erzbir
 * @Date: 2022/12/2 10:44
 * 这里使用装饰者模式
 */
@Getter
@Setter
public class TimeJob implements STask {
    private CronPattern cron; // cron表达式
    private Scheduler scheduler = new Scheduler(); // 使用
    private String name;
    private TimeAction task;
    private Contact contact;

    private TimeJob(String name, CronPattern cron, TimeAction task) {
        this.task = task;
        this.cron = cron;
        this.name = name;
        scheduler.schedule(UUID.randomUUID().toString(), cron, this);
        scheduler.setDaemon(true);
        scheduler.setMatchSecond(true);
    }

    public static TimeJob createTask(String name, String cron, TimeAction task) {
        return new TimeJob(name, CronPattern.of(cron), task);
    }

    public TimeJob setAction(TimeAction task) {
        this.task = task;
        return this;
    }

    @Override
    public void execute() {
        if (task != null) {
            task.execute();
        }
    }

    public void start() {
        scheduler.start();
    }

    public void stop() {
        scheduler.stop();
        scheduler = null;
        cron = null;
        task = null;
        name = null;
    }

    public void stop(boolean clean) {
        scheduler.stop(clean);
        scheduler = null;
        cron = null;
        task = null;
        name = null;
    }
}
