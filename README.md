# Mirai-Numeron

## 介绍:

这是一个spring-boot开发基于mirai的QQ机器人, 使用spring做了一些封装.

目前并没有什么功能(慢慢写), 现在可以把这个当成一个封装的框架来使用,
比如在A方法上加上<code>@GroupMessage</code>注解表示在监听到一个<code>GroupMessageEvent</code>后调用此方法进行对应处理(
注意A方法所属的类必须加<code>@Listener</code>注解或者<code>@Component</code>)

例子:

```java
import com.erzbir.mirai.numeron.annotation.UserMessage;
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

没有使用数据库, 你可以尝试加一个, 所有配置都写在resource下的application.properties中, 通过<code>@Value</code>注入,
如果你的项目中没有这个文件请新建, 格式如下:

```properties
username=292929292929
password=dwoj123132
master=282828282828
illegalList=嗯哼,啊哈
groupList=1312314213,244211314,12421412
blackList=4124114,1231231,421421421
whiteList=525321,3923112,3931313,41421412
```

```text
username: 机器人QQ
password: 机器人QQ密码
master: 机器人主人
illegalList: 违禁词列表
groupList: 启用机器人的群
blackList: 黑名单QQ列表
whiteList: 白名单QQ列表
```

如果想进行添加黑名单等操作, 建议模仿我在controller包下的做法, 使用工厂模式

基本都有一个模板, test包下的<code>Test</code>类是通过注解来处理消息的例子, plugins包下的command包下的<code>CommandExecutor</code>
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

## 联系方式:

email: 2978086497@qq.com

## 博客:

<a href=https://erzbir.com>erzbir.com</a>

## Bug:

咱未发现
