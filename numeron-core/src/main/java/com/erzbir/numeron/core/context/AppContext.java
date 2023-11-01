package com.erzbir.numeron.core.context;

import com.erzbir.numeron.annotation.Component;
import com.erzbir.numeron.annotation.Lazy;
import com.erzbir.numeron.api.context.BeanFactory;
import com.erzbir.numeron.api.processor.Processor;
import com.erzbir.numeron.exception.BeanCreateException;
import com.erzbir.numeron.exception.BeanNotFound;
import com.erzbir.numeron.utils.ClassScanner;
import com.erzbir.numeron.utils.NumeronLogUtil;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

/**
 * @author Erzbir
 * @Date: 2022/12/12 15:14
 * <p>有 {@link Component} 注解标记的类注册到这个类里</p>
 */
public class AppContext implements BeanFactory {
    public static final AppContext INSTANCE = new AppContext();
    public final ExecutorService executor = Executors.newFixedThreadPool(12);
    private final Map<String, Class<?>> context = new ConcurrentHashMap<>();
    private final Map<String, Class<?>> lazyContext = new ConcurrentHashMap<>();
    private final Map<String, Object> singletonMap = new ConcurrentHashMap<>();
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
        classes.forEach(this::addBean);
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

    public void addBean(Class<?> beanClass) {
        Lazy annotation = beanClass.getAnnotation(Lazy.class);
        if (annotation == null || !annotation.value()) {
            try {
                context.put(beanClass.getName(), beanClass);
                if (isConstructClass(beanClass)) {
                    Object value = create(beanClass);
                    singletonMap.put(beanClass.getName(), value);
                }
            } catch (InvocationTargetException | InstantiationException |
                     IllegalAccessException e) {
                NumeronLogUtil.logger.error("ERROR", e);
                throw new BeanCreateException("could not create this bean", e);
            } catch (NoSuchMethodException ignore) {
            }
        } else {
            lazyContext.put(beanClass.getName(), beanClass);
        }
    }

    public void addBean(Object bean) {
        singletonMap.put(bean.getClass().getName(), bean);
    }

    public void addBean(String name, Object bean) {
        singletonMap.put(name, bean);
    }

    private boolean isConstructClass(Class<?> bean) {
        int modifiers = bean.getModifiers();
        return !bean.isAnnotation() && !bean.isEnum() && !bean.isInterface() && !Modifier.isAbstract(modifiers);
    }

    @Override
    public Object getBean(Class<?> requiredType) {
        return getBean(requiredType.getName());
    }

    @Override
    public Object getBean(String name) {
        Object o = singletonMap.get(name);
        if (o == null) {
            o = getLazyBean(name);
        }
        return o;
    }

    @Override
    public boolean containsBean(String name) {
        return context.containsKey(name) || lazyContext.containsKey(name);
    }

    private Object getLazyBean(Class<?> type) {
        return getLazyBean(type.getName());
    }

    private Object getLazyBean(String name) {
        Object o = null;
        try {
            Class<?> clazz = lazyContext.get(name);
            if (isConstructClass(clazz)) {
                o = create(clazz);
            }
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                 IllegalAccessException e) {
            NumeronLogUtil.logger.error("ERROR", e);
            throw new BeanCreateException("could not create this bean", e);
        }
        if (o == null) {
            throw new BeanNotFound("not found bean named " + name);
        }
        lazyContext.remove(name);
        context.put(name, o.getClass());
        singletonMap.put(name, o);
        return o;
    }

    private boolean beanIsAssignableFrom(Object bean, Class<?> interfaceType) {
        return interfaceType.isAssignableFrom(bean.getClass());
    }

    private boolean beanIsAnnotationPresent(Object bean, Class<? extends Annotation> annotation) {
        return bean.getClass().isAnnotationPresent(annotation);
    }

    private Object getBeanWithInter(String name, Class<?> interfaceType) {
        Object bean = getBean(name);
        if (beanIsAssignableFrom(bean, interfaceType)) {
            return bean;
        }
        return null;
    }

    private Object getBeanWithAnnotation(String name, Class<? extends Annotation> annotation) {
        Object bean = getBean(name);
        if (beanIsAnnotationPresent(name, annotation)) {
            return bean;
        }
        return null;
    }

    /**
     * @param interfaceType 接口的字节码
     * @return 返回类名为键, 类的字节码为值的Map
     * <p>取出所有实现了{@param interfaceType}接口的类实现</p>
     */
    public Map<String, Object> getBeansWithInter(Class<?> interfaceType) {
        Map<String, Object> beans = new HashMap<>();
        Stream<String> concat = Stream.concat(singletonMap.keySet().stream(), lazyContext.keySet().stream());
        concat.forEach(k -> {
            Object bean = getBean(k);
            if (beanIsAssignableFrom(bean, interfaceType)) {
                beans.put(k, bean);
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
        Stream<String> concat = Stream.concat(singletonMap.keySet().stream(), lazyContext.keySet().stream());
        concat.forEach(k -> {
            Object bean = getBean(k);
            if (beanIsAnnotationPresent(bean, annotationType)) {
                beans.put(k, bean);
            }
        });
        return beans;
    }

    public void removeContext(String name) {
        Object remove = context.remove(name);
        if (remove == null) {
            lazyContext.remove(name);
        }
    }

    public void removeContext(Class<?> beanType) {
        removeContext(beanType.getName());
    }

    public Object removeBean(String name) {
        removeContext(name);
        return singletonMap.remove(name);
    }

    public Object removeBean(Class<?> beanType) {
        return removeBean(beanType.getName());
    }

    public Map<String, Object> getBeanMap() {
        return singletonMap;
    }

    public List<Object> getBeans() {
        return List.of(singletonMap.values());
    }

    public Map<String, Class<?>> getContextMap() {
        Map<String, Class<?>> map = new HashMap<>();
        map.putAll(context);
        map.putAll(lazyContext);
        return map;
    }
}