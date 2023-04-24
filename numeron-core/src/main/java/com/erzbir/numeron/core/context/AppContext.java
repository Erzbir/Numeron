package com.erzbir.numeron.core.context;

import com.erzbir.numeron.core.classloader.ClassScanner;
import com.erzbir.numeron.core.exception.AppContextException;
import com.erzbir.numeron.core.handler.Component;
import com.erzbir.numeron.core.utils.NumeronLogUtil;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Erzbir
 * @Date: 2022/12/12 15:14
 * <p>有@Componet注解或是基础@Component注解的类实例化后注册到这个包装类里</p>
 */
@SuppressWarnings("unchecked")
public class AppContext implements BeanFactory {
    public static final AppContext INSTANT = new AppContext();
    private final HashMap<String, Object> context = new HashMap<>();

    private AppContext() {
        this("com.erzbir.numeron");
    }

    private AppContext(String packageName) {
        addAllToContext(packageName);
    }

    public void addAllToContext(String packageName) {
        try {
            ClassScanner scanner = new ClassScanner(packageName, AppContext.class.getClassLoader(), true, null, null);
            Set<Class<?>> classes = scanner.scanWithAnnotation(Component.class); // 扫瞄带有@Component注解的class
            addAllToContext(classes);
        } catch (ClassNotFoundException | IOException e) {
            NumeronLogUtil.err(e.getMessage());
            throw new AppContextException(e);
        }
    }

    private void addAllToContext(Set<Class<?>> classes) {
        classes.forEach(e -> {
            try {
                if (isConstructClass(e)) {
                    addToContext(e);  // 判断是否为可实例化的类
                }
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException ex) {
                NumeronLogUtil.err(ex.getMessage());
                throw new AppContextException(ex);
            }
        });
    }

    private void addToContext(Class<?> bean) throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        context.put(bean.getName(), create(bean));
    }

    private boolean isConstructClass(Class<?> bean) {
        return !bean.isAnnotation() && !bean.isEnum() && !bean.isInterface();
    }

    @Override
    public <T> T getBean(Class<T> requiredType) {
        return getBean(requiredType.getName());
    }

    @Override
    public <T> T getBean(String name) {
        return (T) context.get(name);
    }

    @Override
    public boolean containsBean(String name) {
        return context.containsKey(name);
    }

    /**
     * @param interfaceType 接口的字节码
     * @param <T>           泛型
     * @return 返回类名为键, 类的字节码为值的Map
     * <p>取出所有实现了{@param interfaceType}接口的类实现</p>
     */
    public <T> Map<String, T> getBeansWithInter(Class<T> interfaceType) {
        HashMap<String, T> beans = new HashMap<>();
        context.forEach((k, v) -> {
            // 判断v是否实现这个接口
            if (interfaceType.isAssignableFrom(v.getClass())) {
                beans.put(k, (T) v);
            }
        });
        return beans;
    }

    /**
     * @param annotationType 注解的字节码
     * @param <T>            泛型
     * @return 返回类名为键, 类的字节码为值的Map
     * <p>取出所有带有{@param annotationType}注解的类<p/>
     */
    @Override
    public <T> Map<String, T> getBeansWithAnnotation(Class<? extends Annotation> annotationType) {
        HashMap<String, T> beans = new HashMap<>();
        context.forEach((k, v) -> {
            if (v.getClass().getAnnotation(annotationType) != null) {
                beans.put(k, (T) v);
            }
        });
        return beans;
    }

    public HashMap<String, Object> getContext() {
        return context;
    }
}