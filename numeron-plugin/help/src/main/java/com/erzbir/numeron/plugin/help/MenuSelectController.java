package com.erzbir.numeron.plugin.help;

import com.erzbir.numeron.core.filter.message.MessageRule;
import com.erzbir.numeron.core.filter.permission.PermissionType;
import com.erzbir.numeron.core.filter.rule.FilterRule;
import com.erzbir.numeron.core.listener.Listener;
import com.erzbir.numeron.core.listener.massage.Message;
import com.erzbir.numeron.menu.Menu;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.MessageEvent;

import static com.erzbir.numeron.menu.IOUtils.bufferedImageToInputStream;
import static com.erzbir.numeron.menu.MenuDrawUtil.drawMenu;
import static com.erzbir.numeron.menu.MenuHelpDrawUtil.drawMenuHelp;
import static com.erzbir.numeron.menu.MenuStatic.menuList;
import static com.erzbir.numeron.menu.MenuStatic.menuMap;


@Listener
@Menu(name = "菜单")
@SuppressWarnings("unused")
public class MenuSelectController {
    @Message(
            messageRule = MessageRule.EQUAL,
            text = "#menu",
            permission = PermissionType.ALL,
            filterRule = FilterRule.BLACK
    )
    private void picMenu(MessageEvent event) {
        event.getSubject().sendMessage(Contact.uploadImage(event.getSubject(), bufferedImageToInputStream(drawMenu(event.getSubject().getId()), "PNG")));
    }

    @Message(
            messageRule = MessageRule.REGEX,
            text = "#help\\s+.+",
            permission = PermissionType.ALL,
            filterRule = FilterRule.BLACK
    )
    private void subMenu(GroupMessageEvent event) {
        String s = event.getMessage().contentToString().replaceFirst("#help\\s+", "");
        if (menuList.contains(s)) {
            if (menuMap.get(s) != null && menuMap.get(s).size() > 0)
                event.getSubject().sendMessage(Contact.uploadImage(event.getSubject(), bufferedImageToInputStream(drawMenuHelp(s), "PNG")));
            else event.getSubject().sendMessage("该功能没有具体使用说明");
        } else event.getSubject().sendMessage("未找到该功能");
    }
}