package com.erzbir.numeron.menu;


import com.erzbir.numeron.core.entity.GroupList;
import com.erzbir.numeron.core.handler.Command;
import com.erzbir.numeron.core.processor.AppContext;
import com.erzbir.numeron.core.processor.Processor;
import com.erzbir.numeron.core.utils.MiraiLogUtil;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Erzbir
 * @Date: 2023/3/15 19:29
 */
@SuppressWarnings("unused")
public class MenuAnnotationProcessor implements Processor {

    @Override
    public void onApplicationEvent() {
        Map<String, Object> menu = AppContext.INSTANT.getBeansWithAnnotation(Menu.class);
        MiraiLogUtil.verbose("开始生成图片帮助菜单");
        menu.forEach((k, v) -> {
            Menu annotation = v.getClass().getAnnotation(Menu.class);
            String name = annotation.name();
            boolean open = annotation.open();
            MenuStatic.menuList.add(name);
            if (!open) {
                MenuStatic.closeMenuGroups.put(name, GroupList.INSTANCE.getGroup());
            }
            List<Command> commands = new ArrayList<>();
            MiraiLogUtil.info(name);
            for (Method method : v.getClass().getDeclaredMethods()) {
                Command command = method.getAnnotation(Command.class);
                if (command == null) {
                    continue;
                }
                commands.add(command);
            }
            if (MenuStatic.menuMap.containsKey(name)) {
                MenuStatic.menuMap.get(name).addAll(commands);
            } else {
                MenuStatic.menuMap.put(name, commands);
            }
        });
        MiraiLogUtil.verbose("图片帮助菜单生成完成\n");
    }
}




