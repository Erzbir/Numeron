package com.erzbir.mirai.numeron.plugins.codeprocess;

import com.erzbir.mirai.numeron.enums.FilterRule;
import com.erzbir.mirai.numeron.enums.MessageRule;
import com.erzbir.mirai.numeron.enums.PermissionType;
import com.erzbir.mirai.numeron.listener.Listener;
import com.erzbir.mirai.numeron.listener.massage.Message;
import com.erzbir.mirai.numeron.plugins.codeprocess.runner.CodeRunner;
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
        event.getSubject().sendMessage(codeRunner.getRunCode().execute(event.getMessage().contentToString().replaceFirst("py\\s+", "")));
    }

    @Message(text = "js\n", messageRule = MessageRule.BEGIN_WITH, filterRule = FilterRule.NONE, permission = PermissionType.MASTER)
    public void runJs(MessageEvent event) throws IOException {
        codeRunner.setRunCode(RunJs.getInstance());
        event.getSubject().sendMessage(codeRunner.getRunCode().execute(event.getMessage().contentToString().replaceFirst("js\\s+", "")));
    }

    @Message(text = "sh\n", messageRule = MessageRule.BEGIN_WITH, filterRule = FilterRule.NONE, permission = PermissionType.MASTER)
    public void runShell(MessageEvent event) throws IOException {
        codeRunner.setRunCode(RunShell.getInstance());
        event.getSubject().sendMessage(codeRunner.getRunCode().execute(event.getMessage().contentToString().replaceFirst("sh\\s+", "")));
    }
}
