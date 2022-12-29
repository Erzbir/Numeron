package com.erzbir.mirai.numeron.job.inter;

import cn.hutool.cron.task.Task;

/**
 * @author Erzbir
 * @Date: 2022/12/5 17:22
 */
public abstract class TimeTask implements Task {
    protected String id;
    protected String cron;

    public TimeTask(String id) {
        this.id = id;
    }

    public abstract String getId();

    public abstract void setId(String id);

    public abstract String getCron();

    public abstract void setCron(String cron);
}
