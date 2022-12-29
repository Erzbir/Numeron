package com.erzbir.mirai.numeron.bot.codeprocess;

import com.erzbir.mirai.numeron.bot.codeprocess.runner.CodeRunner;
import com.erzbir.mirai.numeron.bot.codeprocess.runway.RunJs;
import com.erzbir.mirai.numeron.bot.codeprocess.runway.RunPy;
import com.erzbir.mirai.numeron.bot.codeprocess.runway.RunShell;
import com.erzbir.mirai.numeron.filter.message.MessageRule;
import com.erzbir.mirai.numeron.filter.permission.PermissionType;
import com.erzbir.mirai.numeron.filter.rule.FilterRule;
import com.erzbir.mirai.numeron.handler.Command;
import com.erzbir.mirai.numeron.listener.Listener;
import com.erzbir.mirai.numeron.listener.massage.Message;
import net.mamoe.mirai.event.events.MessageEvent;

import java.io.IOException;

/**
 * @author Erzbir
 * @Date: 2022/11/30 09:34
 */
@Listener
@SuppressWarnings("unused")
public class Processor {
    private final CodeRunner codeRunner = CodeRunner.getInstance();

    @Command(name = "指令执行", dec = "执行py代码", help = "/py\nprint(1)")
    @Message(text = "py\n", messageRule = MessageRule.BEGIN_WITH, filterRule = FilterRule.NONE, permission = PermissionType.MASTER)
    private void runPy(MessageEvent event) throws IOException {
        codeRunner.setRunCode(RunPy.getInstance());
        event.getSubject().sendMessage(codeRunner.getRunCode().execute(event.getMessage().contentToString().replaceFirst("py\\s+", "")));
    }

    @Command(name = "指令执行", dec = "执行js代码", help = "/py\nconsole.log(1)")
    @Message(text = "js\n", messageRule = MessageRule.BEGIN_WITH, filterRule = FilterRule.NONE, permission = PermissionType.MASTER)
    private void runJs(MessageEvent event) throws IOException {
        codeRunner.setRunCode(RunJs.getInstance());
        event.getSubject().sendMessage(codeRunner.getRunCode().execute(event.getMessage().contentToString().replaceFirst("js\\s+", "")));
    }

    @Command(name = "指令执行", dec = "执行shell代码", help = "sh\necho 1")
    @Message(text = "sh\n", messageRule = MessageRule.BEGIN_WITH, filterRule = FilterRule.NONE, permission = PermissionType.MASTER)
    private void runShell(MessageEvent event) throws IOException {
        codeRunner.setRunCode(RunShell.getInstance());
        event.getSubject().sendMessage(codeRunner.getRunCode().execute(event.getMessage().contentToString().replaceFirst("sh\\s+", "")));
    }
}
