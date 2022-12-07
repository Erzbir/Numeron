package com.erzbir.mirai.numeron.job.inter;

import cn.hutool.cron.task.Task;

/**
 * @author Erzbir
 * @Date: 2022/12/8 03:04
 */
public interface CloseAction extends Task {
    void stop();
}
