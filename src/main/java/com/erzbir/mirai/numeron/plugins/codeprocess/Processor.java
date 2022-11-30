package com.erzbir.mirai.numeron.plugins.codeprocess;

import com.erzbir.mirai.numeron.enums.FilterRule;
import com.erzbir.mirai.numeron.enums.MessageRule;
import com.erzbir.mirai.numeron.enums.PermissionType;
import com.erzbir.mirai.numeron.listener.Listener;
import com.erzbir.mirai.numeron.listener.massage.Message;
import com.erzbir.mirai.numeron.plugins.codeprocess.runner.CodeRunner;
import com.erzbir.mirai.numeron.plugins.codeprocess.runway.RunJava;
import com.erzbir.mirai.numeron.plugins.codeprocess.runway.RunJs;
import com.erzbir.mirai.numeron.plugins.codeprocess.runway.RunPy;
import com.erzbir.mirai.numeron.plugins.codeprocess.runway.RunShell;
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

    @Message(text = "py\n", messageRule = MessageRule.BEGIN_WITH, filterRule = FilterRule.NONE, permission = PermissionType.MASTER)
    public void runPy(MessageEvent event) throws IOException {
        codeRunner.setRunCode(RunPy.getInstance());
        event.getSubject().sendMessage(codeRunner.getRunCode().execute(event.getMessage().contentToString().split("\\s+")[1]));
    }

    @Message(text = "js\n", messageRule = MessageRule.BEGIN_WITH, filterRule = FilterRule.NONE, permission = PermissionType.MASTER)
    public void runJs(MessageEvent event) throws IOException {
        codeRunner.setRunCode(RunJs.getInstance());
        event.getSubject().sendMessage(codeRunner.getRunCode().execute(event.getMessage().contentToString().split("\\s+")[1]));
    }

    @Message(text = "java\n", messageRule = MessageRule.BEGIN_WITH, filterRule = FilterRule.NONE, permission = PermissionType.MASTER)
    public void runJava(MessageEvent event) throws IOException {
        codeRunner.setRunCode(RunJava.getInstance());
        event.getSubject().sendMessage(codeRunner.getRunCode().execute(event.getMessage().contentToString().split("\\s+")[1]));
    }

    @Message(text = "shell\n", messageRule = MessageRule.BEGIN_WITH, filterRule = FilterRule.NONE, permission = PermissionType.MASTER)
    public void runShell(MessageEvent event) throws IOException {
        codeRunner.setRunCode(RunShell.getInstance());
        event.getSubject().sendMessage(codeRunner.getRunCode().execute(event.getMessage().contentToString().split("\\s+")[1]));
    }
}
