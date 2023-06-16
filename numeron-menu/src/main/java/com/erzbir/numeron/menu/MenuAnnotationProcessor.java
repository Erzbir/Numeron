package com.erzbir.numeron.menu;


import com.erzbir.numeron.annotation.Command;
import com.erzbir.numeron.api.processor.Processor;
import com.erzbir.numeron.core.context.AppContext;
import com.erzbir.numeron.core.entity.serviceimpl.GroupServiceImpl;
import com.erzbir.numeron.utils.NumeronLogUtil;

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
        Map<String, Class<?>> menu = AppContext.INSTANCE.getBeansWithAnnotation(Menu.class);
        NumeronLogUtil.info("开始生成图片帮助菜单");
        menu.forEach((k, v) -> {
            Menu annotation = v.getAnnotation(Menu.class);
            String name = annotation.name();
            boolean open = annotation.open();
            MenuStatic.menuList.add(name);
            if (!open) {
                GroupServiceImpl groupService = new GroupServiceImpl();
                MenuStatic.closeMenuGroups.put(name, groupService.getEnableGroupList());
            }
            List<Command> commands = new ArrayList<>();
            NumeronLogUtil.debug(name);
            for (Method method : v.getDeclaredMethods()) {
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
        NumeronLogUtil.info("图片帮助菜单生成完成\n");
    }

    @Override
    public void destroy() {

    }
}




