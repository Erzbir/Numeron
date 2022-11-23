package com.erzbir.mirai.numeron.config.load;

import com.erzbir.mirai.numeron.annotation.sql.DataValue;
import com.erzbir.mirai.numeron.annotation.sql.Loader;
import com.erzbir.mirai.numeron.config.BotConfig;
import com.erzbir.mirai.numeron.sql.SqlUtil;

import java.util.List;

/**
 * @author Erzbir
 * @Date: 2022/11/22 18:01
 */
@Loader
public class SqlLoadToConfig {

    public static void load() {
        List.of(BotConfig.class.getDeclaredFields()).forEach(field -> {
            DataValue annotation = field.getDeclaredAnnotation(DataValue.class);
            if (annotation != null) {
                try {
                    field.set(null, SqlUtil.perms.get(field.getName()));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}