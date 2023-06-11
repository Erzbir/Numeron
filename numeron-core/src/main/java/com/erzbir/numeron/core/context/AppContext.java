package com.erzbir.numeron.core.context;

import com.erzbir.numeron.annotation.Component;
import com.erzbir.numeron.api.processor.Processor;
import com.erzbir.numeron.core.exception.AppContextException;
import com.erzbir.numeron.utils.ClassScanner;
import com.erzbir.numeron.utils.NumeronLogUtil;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Erzbir
 * @Date: 2022/12/12 15:14
 * <p>有@Componet注解或是继承@Component注解的注解的类实例化后注册到这个包装类里</p>
 */
@SuppressWarnings("unchecked")
public class AppContext implements BeanFactory {
    public static final AppContext INSTANCE = new AppContext();
    public final ExecutorService executor = Executors.newFixedThreadPool(12);
    private final Map<String, Object> context = new ConcurrentHashMap<>();
    private final Set<Processor> processors = new HashSet<>();

    private AppContext() {
        this("com.erzbir.numeron");
    }

    private AppContext(String packageName) {
        addAllToContext(packageName, AppContext.class.getClassLoader(), Component.class);
    }

    public void addAllToContext(String packageName, ClassLoader classLoader, Class<? extends Annotation> annotation) {
        try {
            ClassScanner scanner = new ClassScanner(packageName, classLoader, true, null, null);
            Set<Class<?>> classes = scanner.scanWithAnnotation(annotation); // 扫瞄带有@Component注解的class
            addAllToContext(classes);
        } catch (ClassNotFoundException | IOException e) {
            NumeronLogUtil.err(e.getMessage());
            throw new AppContextException(e);
        }
    }


    private void addAllToContext(Set<Class<?>> classes) {
        classes.forEach(e -> {
            if (isConstructClass(e)) {
                try {
                    addToContext(e);  // 判断是否为可实例化的类
                } catch (InvocationTargetException | InstantiationException | IllegalAccessException |
                         NoSuchMethodException ex) {
                    NumeronLogUtil.err(ex.getMessage());
                    throw new AppContextException(ex);
                }
            }
        });
    }

    public synchronized void addProcessor(Processor processor) {
        processors.add(processor);
    }

    public synchronized void removeProcessor(Processor processor) {
        processors.remove(processor);
    }

    public Set<Processor> getProcessors() {
        return processors;
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

    public Object removeBean(String name) {
        return context.remove(name);
    }

    public Object removeBean(Class<?> beanType) {
        return removeBean(beanType.getName());
    }

    public Map<String, Object> getContext() {
        return context;
    }
}