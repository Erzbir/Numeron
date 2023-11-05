package com.erzbir.numeron.console.plugin;

import com.erzbir.numeron.api.context.DefaultBeanCentral;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.File;
import java.util.Set;

/**
 * @author Erzbir
 * @Date: 2023/6/16 09:58
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class PluginContext {
    private Set<Class<?>> classes;
    private File file;


    public void destroy() {
        if (classes == null) {
            return;
        }
        classes.forEach(t -> {
            DefaultBeanCentral.INSTANCE.reduce(t);
            DefaultBeanCentral.INSTANCE.remove(t.getName());
        });
        classes.clear();
        classes = null;
        System.gc();
    }
}