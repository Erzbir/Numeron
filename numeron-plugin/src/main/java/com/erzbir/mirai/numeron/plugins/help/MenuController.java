package com.erzbir.mirai.numeron.plugins.help;


import com.erzbir.mirai.numeron.filter.message.MessageRule;
import com.erzbir.mirai.numeron.filter.permission.PermissionType;
import com.erzbir.mirai.numeron.listener.Listener;
import com.erzbir.mirai.numeron.listener.massage.GroupMessage;
import com.erzbir.mirai.numeron.menu.MenuStatic;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.event.events.MessageEvent;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

import static com.erzbir.mirai.numeron.menu.IOUtils.bufferedImageToInputStream;
import static com.erzbir.mirai.numeron.menu.MenuDrawUtil.drawMenu;


@Listener
@SuppressWarnings("unused")
public class MenuController {
    @GroupMessage(
            text = "^#OpenMenu\\s\\w*$",
            messageRule = MessageRule.REGEX,
            permission = PermissionType.ADMIN
    )
    private void openMenu(@NotNull MessageEvent e) {
        String[] ary = e.getMessage().contentToString().split("\\s+");
        if (MenuStatic.menuList.contains(ary[1]) && MenuStatic.closeMenuGroups.get(ary[1]) != null) {
            MenuStatic.closeMenuGroups.get(ary[1]).remove(e.getSubject().getId());
            e.getSubject().sendMessage(Contact.uploadImage(e.getSubject(), bufferedImageToInputStream(drawMenu(e.getSubject().getId()), "PNG")));
        } else if (MenuStatic.menuList.contains(ary[1])) {
            e.getSubject().sendMessage("该功能已开启");
        } else {
            e.getSubject().sendMessage("未找到该功能");
        }
    }

    @GroupMessage(
            text = "^#CloseMenu\\s\\w*$",
            messageRule = MessageRule.REGEX,
            permission = PermissionType.ADMIN
    )
    private void closeMenu(@NotNull MessageEvent e) {
        String[] ary = e.getMessage().contentToString().split("\\s+");
        if (MenuStatic.menuList.contains(ary[1])) {
            if (MenuStatic.closeMenuGroups.get(ary[1]) != null)
                MenuStatic.closeMenuGroups.get(ary[1]).add(e.getSubject().getId());
            else {
                Set<Long> groups = new HashSet<>();
                groups.add(e.getSubject().getId());
                MenuStatic.closeMenuGroups.put(ary[1], groups);
            }
            e.getSubject().sendMessage(Contact.uploadImage(e.getSubject(), bufferedImageToInputStream(drawMenu(e.getSubject().getId()), "PNG")));
        } else {
            e.getSubject().sendMessage("未找到该功能");
        }
    }
}
