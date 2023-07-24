package com.erzbir.numeron.core.context;

import com.erzbir.numeron.annotation.Component;
import com.erzbir.numeron.annotation.Lazy;
import com.erzbir.numeron.api.processor.Processor;
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
public class AppContext implements BeanFactory {
    public static final AppContext INSTANCE = new AppContext();
    public final ExecutorService executor = Executors.newFixedThreadPool(12);
    private final Map<String, Object> context = new ConcurrentHashMap<>();
    private final Map<String, Class<?>> lazyContext = new ConcurrentHashMap<>();
    private final Set<Processor> processors = new HashSet<>();

    private AppContext() {
        this("com.erzbir.numeron");
    }

    private AppContext(String packageName) {
        addAllToContext(packageName, AppContext.class.getClassLoader(), Component.class);
    }

    public void addAllToContext(String packageName, ClassLoader classLoader, Class<? extends Annotation> annotation) {
        try {
            ClassScanner scanner = new ClassScanner(packageName, classLoader, true, null);
            Set<Class<?>> classes = scanner.scanWithAnnotation(annotation); // 扫瞄带有@Component注解的class
            addAllToContext(classes);
        } catch (ClassNotFoundException | IOException e) {
            NumeronLogUtil.err(e.getMessage());
        }
    }


    private void addAllToContext(Set<Class<?>> classes) {
        classes.forEach(e -> {
            if (isConstructClass(e)) {
                try {
                    addToContext(e);
                } catch (InvocationTargetException | InstantiationException | IllegalAccessException |
                         NoSuchMethodException ex) {
                    NumeronLogUtil.logger.error("ERROR", ex);
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

    public void addToContext(Class<?> bean) throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        if (!isConstructClass(bean)) {
            return;
        }
        Lazy annotation = bean.getAnnotation(Lazy.class);
        if (annotation == null || !annotation.value()) {
            context.put(bean.getName(), create(bean));
        } else {
            lazyContext.put(bean.getName(), bean);
        }
    }

    public void addToContext(Object bean) {
        context.put(bean.getClass().getName(), bean);
    }

    private boolean isConstructClass(Class<?> bean) {
        return !bean.isAnnotation() && !bean.isEnum() && !bean.isInterface();
    }

    @Override
    public Object getBean(Class<?> requiredType) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return getBean(requiredType.getName());
    }

    @Override
    public Object getBean(String name) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Object o = context.get(name);
        if (o == null) {
            o = create(lazyContext.remove(name));
            context.put(name, o);
        }
        return o;
    }

    @Override
    public boolean containsBean(String name) {
        return context.containsKey(name) || lazyContext.containsKey(name);
    }

    /**
     * @param interfaceType 接口的字节码
     * @return 返回类名为键, 类的字节码为值的Map
     * <p>取出所有实现了{@param interfaceType}接口的类实现</p>
     */
    public Map<String, Object> getBeansWithInter(Class<?> interfaceType) {
        Map<String, Object> beans = new HashMap<>();
        context.forEach((k, v) -> {
            if (interfaceType.isAssignableFrom(v.getClass())) {
                beans.put(k, v);
            }
        });
        lazyContext.forEach((k, v) -> {
            if (interfaceType.isAssignableFrom(v)) {
                try {
                    Object value = create(v);
                    beans.put(k, value);
                    lazyContext.remove(k);
                    context.put(k, value);
                } catch (InvocationTargetException | NoSuchMethodException | InstantiationException |
                         IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        return beans;
    }

    /**
     * @param annotationType 注解的字节码
     * @return 返回类名为键, 类的字节码为值的Map
     * <p>取出所有带有{@param annotationType}注解的类<p/>
     */
    @Override
    public Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annotationType) {
        HashMap<String, Object> beans = new HashMap<>();
        context.forEach((k, v) -> {
            if (v.getClass().isAnnotationPresent(annotationType)) {
                beans.put(k, v);
            }
        });
        lazyContext.forEach((k, v) -> {
            if (v.isAnnotationPresent(annotationType)) {
                try {
                    Object value = create(v);
                    beans.put(k, value);
                    lazyContext.remove(k);
                    context.put(k, value);
                } catch (InvocationTargetException | NoSuchMethodException | InstantiationException |
                         IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        return beans;
    }

    public Object removeBean(String name) {
        Object remove = context.remove(name);
        if (remove == null) {
            lazyContext.remove(name);
        }
        return remove;
    }

    public Object removeBean(Class<?> beanType) {
        return removeBean(beanType.getName());
    }
}