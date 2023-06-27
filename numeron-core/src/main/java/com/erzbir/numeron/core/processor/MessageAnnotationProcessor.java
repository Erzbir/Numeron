package com.erzbir.numeron.core.processor;

import com.erzbir.numeron.annotation.Event;
import com.erzbir.numeron.annotation.Listener;
import com.erzbir.numeron.annotation.Message;
import com.erzbir.numeron.api.processor.Processor;
import com.erzbir.numeron.core.context.AppContext;
import com.erzbir.numeron.core.exception.AnnotationGetException;
import com.erzbir.numeron.core.filter.BaseFilter;
import com.erzbir.numeron.core.filter.event.message.AbstractMessageFilter;
import com.erzbir.numeron.core.filter.event.message.IDMessageFilter;
import com.erzbir.numeron.core.filter.event.message.MessageFilterFactory;
import com.erzbir.numeron.core.filter.event.permission.AbstractPermissionFilter;
import com.erzbir.numeron.core.filter.event.permission.PermissionFilterFactory;
import com.erzbir.numeron.core.filter.event.rule.AbstractRuleFilter;
import com.erzbir.numeron.core.filter.event.rule.RuleFilterFactory;
import com.erzbir.numeron.core.handler.factory.ExecutorFactory;
import com.erzbir.numeron.filter.FilterRule;
import com.erzbir.numeron.filter.MessageRule;
import com.erzbir.numeron.filter.PermissionType;
import com.erzbir.numeron.utils.NumeronLogUtil;
import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.GlobalEventChannel;
import net.mamoe.mirai.event.events.BotEvent;
import net.mamoe.mirai.event.events.MessageEvent;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Erzbir
 * @Date: 2022/11/18 15:10
 * <p>
 * 此类为消息处理类, 从bean容器中获取有特定注解的bean, 并根据方法上的注解执行 过滤channel/执行对应方法等
 * </p>
 */
@SuppressWarnings("unused")
public class MessageAnnotationProcessor implements Processor {
    public static EventChannel<net.mamoe.mirai.event.Event> channel;
    public static Map<String, Method> methodMap = new HashMap<>(4);

    static {
        Method[] methods = Message.class.getDeclaredMethods();
        Arrays.stream(methods).forEach(method -> methodMap.put(method.getName().replaceFirst("\\(.*\\)", ""), method));
    }

    /**
     * 通过过滤监听, 最终过滤到一个确定的事件, 过滤规则由注解标记
     *
     * @param channel    未过滤的监听频道
     * @param annotation 消息处理注解, 使用泛型代替实际的注解, 这样做的目的是减少代码量, 用反射的方式还原注解
     * @return EventChannel
     */
    @NotNull
    private <E extends Annotation> EventChannel<net.mamoe.mirai.event.Event> toFilter(@NotNull EventChannel<net.mamoe.mirai.event.Event> channel, E annotation) {
        if (!(annotation instanceof Message)) {
            return channel;
        }
        Class<? extends Annotation> aClass = annotation.annotationType();
        FilterRule filterRule;
        MessageRule messageRule;
        String text;
        PermissionType permission;
        long id;
        try {
            // 以下操作通过反射调用注解的方法(注解的属性实际上是一个有返回值的方法), 再强制类型转换成对应的类型
            filterRule = (FilterRule) methodMap.get("filterRule").invoke(annotation);
            messageRule = (MessageRule) methodMap.get("messageRule").invoke(annotation);
            text = (String) methodMap.get("text").invoke(annotation);
            permission = (PermissionType) methodMap.get("permission").invoke(annotation);
            id = (long) methodMap.get("id").invoke(annotation);
        } catch (Exception e) {
            NumeronLogUtil.logger.error("ERROR", e);
            throw new AnnotationGetException(e);
        }
//        BaseFilter baseFilter = new BaseFilter();
//        EventFilter<?> idMessageEventFilter = new IDMessageFilter(baseFilter);
//        EventFilter<?> messageEventFilter = MessageFilterFactory.INSTANCE.create(messageRule, idMessageEventFilter).setText(text).setId(id);
//        EventFilter<?> permissionEventFilter = PermissionFilterFactory.INSTANCE.create(permission, messageEventFilter);
//        EventFilter<?> ruleEventFilter = RuleFilterFactory.INSTANCE.create(filterRule, permissionEventFilter);
//        FinalFilter filter = new FinalFilter(ruleEventFilter);
        return channel.filter(event -> {
            BaseFilter baseFilter = new BaseFilter();
            MessageEvent event1 = (MessageEvent) event;
            AbstractMessageFilter idMessageEventFilter = new IDMessageFilter(baseFilter);
            idMessageEventFilter.filter(event1);
            AbstractMessageFilter messageEventFilter = (AbstractMessageFilter) MessageFilterFactory.INSTANCE.create(messageRule, idMessageEventFilter).setText(text).setId(id);
            messageEventFilter.filter(event1);
            AbstractPermissionFilter permissionEventFilter = PermissionFilterFactory.INSTANCE.create(permission, messageEventFilter);
            permissionEventFilter.filter(event1);
            AbstractRuleFilter ruleEventFilter = RuleFilterFactory.INSTANCE.create(filterRule, permissionEventFilter);
            ruleEventFilter.filter(event1);
            return ruleEventFilter.filter();
        });
        //  return channel.filter(t -> ruleEventFilter.filter());
    }

    /**
     * @param bean       bean对象
     * @param method     反射获取到的bean对象的方法
     * @param channel    过滤的channel
     * @param annotation 消息处理注解
     */
    private <E extends Annotation> void execute(Object bean, Method method, @NotNull EventChannel<net.mamoe.mirai.event.Event> channel, E annotation) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        ExecutorFactory.INSTANCE.create(annotation)
                .getExecute()
                .execute(method, bean, channel, annotation);
    }

    /**
     * @param annotation 注解
     * @return 是否是需要的注解
     */
    private boolean isNeededAnnotation(Annotation annotation) {
        return annotation instanceof Message || annotation instanceof Event;
    }

    /**
     * 执行消息处理方法的注册方法
     *
     * @param v bean对象
     */
    private void registerMethods(Class<?> v) {
        String name = v.getName();
        NumeronLogUtil.debug("扫瞄到 " + name);
        for (Method method : v.getDeclaredMethods()) {
            Arrays.stream(method.getAnnotations())
                    .filter(this::isNeededAnnotation).forEach(annotation -> {
                        String s = Arrays.toString(method.getParameterTypes())
                                .replaceAll("\\[", "(")
                                .replaceAll("]", ")");
                        method.setAccessible(true);
                        try {
                            execute(AppContext.INSTANCE.getBean(v), method, toFilter(channel, annotation), annotation);
                        } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException |
                                 InstantiationException e) {
                            NumeronLogUtil.err(e.getMessage());
                            throw new RuntimeException(e);
                        }
                        NumeronLogUtil.info(method.getName() + s + " 处理方法注册完毕");
                    });
        }
    }

    @Override
    public void onApplicationEvent() {
        AppContext context = AppContext.INSTANCE;
        MessageAnnotationProcessor.channel = GlobalEventChannel.INSTANCE.filter(t -> t instanceof BotEvent);
        NumeronLogUtil.trace("开始注册注解消息处理监听......");
        context.getBeansWithAnnotation(Listener.class).forEach((k, v) -> registerMethods(v));
        NumeronLogUtil.trace("注解消息处理监听注册完毕\n");
    }

    @Override
    public void destroy() {

    }
}