package com.erzbir.mirai.numeron.plugins.help;


import com.erzbir.mirai.numeron.filter.message.MessageRule;
import com.erzbir.mirai.numeron.filter.permission.PermissionType;
import com.erzbir.mirai.numeron.filter.rule.FilterRule;
import com.erzbir.mirai.numeron.listener.Listener;
import com.erzbir.mirai.numeron.listener.massage.Message;
import com.erzbir.mirai.numeron.menu.Menu;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.MessageEvent;

import static com.erzbir.mirai.numeron.menu.IOUtils.bufferedImageToInputStream;
import static com.erzbir.mirai.numeron.menu.MenuDrawUtil.drawMenu;
import static com.erzbir.mirai.numeron.menu.MenuHelpDrawUtil.drawMenuHelp;
import static com.erzbir.mirai.numeron.menu.MenuStatic.menuList;
import static com.erzbir.mirai.numeron.menu.MenuStatic.menuMap;


@Listener
@Menu(name = "菜单")
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
        String s = event.getMessage().contentToString().replaceFirst("#menu\\s+", "");
        if (menuList.contains(s)) {
            if (menuMap.get(s) != null && menuMap.get(s).size() > 0)
                event.getSubject().sendMessage(Contact.uploadImage(event.getSubject(), bufferedImageToInputStream(drawMenuHelp(s), "PNG")));
            else event.getSubject().sendMessage("该功能没有具体使用说明");
        } else event.getSubject().sendMessage("未找到该功能");
    }
}