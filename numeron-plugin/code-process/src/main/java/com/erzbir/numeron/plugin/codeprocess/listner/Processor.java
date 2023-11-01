package com.erzbir.numeron.plugin.codeprocess.listner;

import com.erzbir.numeron.annotation.*;
import com.erzbir.numeron.api.permission.PermissionType;
import com.erzbir.numeron.enums.MatchType;
import com.erzbir.numeron.menu.Menu;
import com.erzbir.numeron.plugin.codeprocess.runner.CodeRunner;
import com.erzbir.numeron.plugin.codeprocess.runway.RunJs;
import com.erzbir.numeron.plugin.codeprocess.runway.RunPy;
import com.erzbir.numeron.plugin.codeprocess.runway.RunShell;
import net.mamoe.mirai.event.events.MessageEvent;

import java.io.IOException;

/**
 * @author Erzbir
 * @Date: 2022/11/30 09:34
 */
@Listener
@Menu(name = "代码执行")
@SuppressWarnings("unused")
public class Processor {
    private final CodeRunner codeRunner = CodeRunner.getInstance();

    @Command(
            name = "指令执行",
            dec = "执行py代码",
            help = "py\nprint(1)",
            permission = PermissionType.MASTER
    )
    @Handler
    @Permission(permission = PermissionType.MASTER)
    @Filter(value = "py\n", matchType = MatchType.TEXT_STARTS_WITH)
    private void runPy(MessageEvent event) throws IOException {
        codeRunner.setRunCode(RunPy.getInstance());
        event.getSubject().sendMessage(codeRunner.getRunCode().execute(event.getMessage().contentToString().replaceFirst("py\\s+", "")));
    }

    @Command(
            name = "指令执行",
            dec = "执行js代码",
            help = "js\nconsole.log(1)",
            permission = PermissionType.MASTER
    )
    @Handler
    @Permission(permission = PermissionType.MASTER)
    @Filter(value = "js\n", matchType = MatchType.TEXT_STARTS_WITH)
    private void runJs(MessageEvent event) throws IOException {
        codeRunner.setRunCode(RunJs.getInstance());
        event.getSubject().sendMessage(codeRunner.getRunCode().execute(event.getMessage().contentToString().replaceFirst("js\\s+", "")));
    }

    @Command(
            name = "指令执行",
            dec = "执行shell代码",
            help = "sh\necho 1",
            permission = PermissionType.MASTER
    )
    @Handler
    @Permission(permission = PermissionType.MASTER)
    @Filter(value = "sh\n", matchType = MatchType.TEXT_STARTS_WITH)
    private void runShell(MessageEvent event) throws IOException {
        codeRunner.setRunCode(RunShell.getInstance());
        event.getSubject().sendMessage(codeRunner.getRunCode().execute(event.getMessage().contentToString().replaceFirst("sh\\s+", "")));
    }
}
