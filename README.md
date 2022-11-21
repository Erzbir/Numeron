# Mirai-Numeron

## 介绍:

这是一个spring-boot开发基于mirai的QQ机器人, 使用spring做了一些封装.

目前并没有什么功能(慢慢写), 现在可以把这个当成一个封装的框架来使用,
比如在A方法上加上<code>@GroupMessage</code>注解表示在监听到一个<code>GroupMessageEvent</code>后调用此方法进行对应处理(
注意A方法所属的类必须加<code>@Listener</code>注解或者<code>@Component</code>)

例子:

```java
import com.erzbir.mirai.numeron.annotation.massage.UserMessage;
import com.erzbir.mirai.numeron.enums.MessageRule;
import net.mamoe.mirai.event.events.UserMessageEvent;
import org.jetbrains.annotations.NotNull;

@Listener
public class Test {
    // 注解中如果filterRule默认值是NONE表示不开启过滤, NORMAL表示过滤掉掉groupList以外的群

    @GroupMessage (messageRule = MessageRule.REGEX, text = "\\d+", permission = PermissionType.ALL, filterRule = FilterRule.BLACKLIST)
    // 处理群消息事件, 正则匹配模式, 匹配数字, 权限是所有人, 过滤规则是过滤掉黑名单
    public void regex(@NotNull GroupMessageEvent event) {
        event.getSubject().sendMessage("这是一个数字");
    }

    @UserMessage (text = "hi", permission = PermissionType.WHITE, filterRule = FilterRule.NONE)
    // 处理消息用户消息事件 匹配"hi", 权限是白名单, 不过滤
    public void sayHello(@NotNull UserMessageEvent event) {
        event.getSubject().sendMessage("hi");
    }

    @Message (text = "晚安", permission = PermissionType.MASTER, filterRule = FilterRule.NORMAL)
    // 匹配消息事件 匹配"晚安", 权限是主人, 过滤掉groupList以外的群 
    public void sayGoodNight(@NotNull MessageEvent event) {
        event.getSubject().sendMessage("晚安");
    }

    // 权限是所有人, 不过滤
    @Message (text = "你好", permission = PermissionType.ALL, filterRule = FilterRule.NONE)
    public void sayH(@NotNull MessageEvent e) {
        e.getSubject().sendMessage("你好");
    }
}

```

## 说明:

第一次使用会使用控制台输入配置, 登陆后则会自动登陆, 生成的文件逻辑看源码吧, 在<code>BotConfig</code>类的<code>
save()</code>方法

如果想进行添加黑名单删改等等操作, 建议模仿我在controller包下的做法, 使用工厂模式

基本都有一个模板, test包下的<code>Test</code>类是通过注解来处理消息的例子, plugins包下的command包下的<code>
CommandExecutor</code>
类是直接在消息中匹配关键词的例子

很多包下面什么都没有, 慢慢写吧.......

## 原理:

在processor包下的两个类 <code>MessageAnnotationProcessor</code>和<code>PluginAnnotationProcessor</code>

1. <code>MessageAnnotationProcessor</code>:

   获取所有消息处理方法的注解, 根据注解的值将Channel进行过滤, 再通过反射调用到匹配到的方法
2. <code>PluginAnnotationProcessor</code>:

   如果你不喜欢用注解来实现的方式, 你可以通过实现<code>PluginRegister</code>接口, 当你实现了<code>register()</code>方法后,
   这个类会先获取所有过滤器执行过滤方法,
   再扫瞄实现了<code>PluginRegister</code>接口的类并为他们注册, 同时为他们传递一个过滤后的<code>Channel</code>

   > 实现过滤的做法是: 将所有把实现了<code>ChannelFilterInter</code>的bean对象取出来, 执行过滤方法之后再放进去

权限控制和消息匹配的原理都是通过过滤<code>Channel</code>实现, 在<code>MessageAnnotationProcessor</code>中实现

配置加载方式是 数据库 + 文件IO + 反射

成员变量上加上<code>@DataValue</code>可以通过数据库注入, 目前没有完善, 如果自定义则需要修改<code>SqlUtil</code>中的代码.
> 注入的逻辑是: 在初始化时将数据库数据解析成<code>HashMap<String, HashSet<String, Object>></code>, 通过反射获取<code>
> filedName</code>在通过<code>filedName</code>在<code>HashMap</code>中取对应的<code>HashSet</code>

## 联系方式:

email: 2978086497@qq.com

## 博客:

<a href=https://erzbir.com>erzbir.com</a>

## Bug:

暂未发现
