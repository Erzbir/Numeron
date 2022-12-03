# Mirai-Numeron

# <b>请给本项目点一个星吧!</b>

## 介绍:

这是一个SpringBoot开发基于Mirai的QQ机器人.

此项目也可作为java基础的实训项目

这个项目的话只需要熟悉反射和会一点SpringBoot就可以了, 希望有个朋友能戳最后面的联系方式

可以把这个项目当成一个<b>脚手架</b>来使用,

本项目使用的设计模式:

- 简单工厂模式
- 抽象工厂模式
- 策略模式
- 简单工厂模式 + 策略模式结合

三个消息处理注解:

- <code>@Message</code> 可以标记在所有消息事件类型的处理方法上
- <code>@GroupMessage</code> 标记在群消息事件处理的方法上
- <code>@UserMessage</code> 标记在好友消息事件处理的方法上

可以用消息处理注解做到什么?

- 三种过滤规则: 不过滤 / 过滤黑名单 / 正常过滤
- 三种权限规则: 主人 / 白名单 / 所有人
- 六种消息匹配规则: 以?开头 / 以?结尾 / 包含? / 相等 / 正则 / 在数组中?

## 说明:

第一次使用会使用控制台输入配置, 登陆后则会自动登陆, 生成的文件逻辑看源码吧, 在<code>BotConfig</code>类的<code>
save()</code>方法

### 此项目部分功能用到了redis, 请先下载安装redis, 否则无法使用相应功能

<b>将所有QQ机器人功能写在plugins目录下</b>

### plugins包下实现的功能:

- 消息回复
- @禁言, qq号禁言
- 全体禁言
- 黑名单检测
- 违禁词检测
- 指令添加关键词回复
- 防撤回
- 自动保存图片和文件
- 发送指定图片和文件 / 保存指定消息(图片等)
- 从指定url下载文件
- 代码和命令执行, 支持JavaScript/Python/Shell
- 定时消息推送 / 定时禁言和解禁言 / 其他定时任务(实现job包下的AbstractAction)

> 在消息事件处理的方法上打上对应注解就可以监听到符合规则的消息后自动执行
>
> 比如在A方法上加上<code>@GroupMessage()</code>注解表示在监听到一个<code>GroupMessageEvent</code>
> 后调用此方法进行对应处理(
> 注意A方法所属的类必须加<code>@Listener</code>注解)

例子:

```java
import com.erzbir.mirai.numeron.listener.massage.GroupMessage;
import com.erzbir.mirai.numeron.listener.massage.UserMessage;
import com.erzbir.mirai.numeron.enums.MessageRule;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.UserMessageEvent;
import org.jetbrains.annotations.NotNull;

@Listener
public class Test {
    // 消息匹配规则设置了默认值, 默认是equals()完全匹配

    @GroupMessage(messageRule = MessageRule.REGEX, text = "\\d+", permission = PermissionType.ALL, filterRule = FilterRule.BLACKLIST)
    // 处理群消息事件, 正则匹配模式, 匹配数字, 权限是所有人, 过滤规则是过滤掉黑名单
    public void regex(@NotNull GroupMessageEvent event) {
        event.getSubject().sendMessage("这是一个数字");
    }

    @UserMessage(text = "hi", permission = PermissionType.WHITE, filterRule = FilterRule.NONE)
    // 处理消息用户消息事件 匹配"hi", 权限是白名单, 不过滤
    public void sayHello(@NotNull UserMessageEvent event) {
        event.getSubject().sendMessage("hi");
    }

    @Message(text = "晚安", permission = PermissionType.MASTER, filterRule = FilterRule.NORMAL)
    // 匹配消息事件 匹配"晚安", 权限是主人, 过滤掉groupList以外的群 
    public void sayGoodNight(@NotNull MessageEvent event) {
        event.getSubject().sendMessage("晚安");
    }

    // 权限是所有人, 不过滤
    @Message(text = "你好", permission = PermissionType.ALL, filterRule = FilterRule.NONE)
    public void sayH(@NotNull MessageEvent e) {
        e.getSubject().sendMessage("你好");
    }

    // 这是一个较为复杂的例子, 禁言一个人, 支持@和qq号
    @Message(text = "/mute\\s+?@?(\\d+?) (\\d+)", filterRule = FilterRule.NONE, messageRule = MessageRule.REGEX, permission = PermissionType.MASTER)
    public void muteSingle(@NotNull MessageEvent event) {
        String[] s = event.getMessage().contentToString().split("\\s+");
        long id;
        int time;
        s[1] = s[1].replaceAll("@", "");
        id = Long.parseLong(s[1]);
        time = Integer.parseInt(s[2]);
        if (event instanceof GroupMessageEvent event1) {
            Objects.requireNonNull(event1.getGroup().get(id)).mute(time);
        } else {
            AtomicReference<NormalMember> member = new AtomicReference<>();
            GlobalConfig.groupList.forEach(v -> member.set(Objects.requireNonNull(event.getBot().getGroup(v)).get(id)));
            if (member.get().getPermission().getLevel() < 1) {
                member.get().mute(time);
            }
        }
    }

    // 这个例子是检测黑名单用户, 正则匹配所有, 不进行任何过滤和权限限制以达到实时检测所有消息发送者的目的. 这样的实现很不好, 因为会时刻都在执行这个方法, 会重新写一个只针对黑名单用户的检测
    @GroupMessage(text = ".*", filterRule = FilterRule.NONE, messageRule = MessageRule.REGEX, permission = PermissionType.ALL)
    public void scan(GroupMessageEvent event) {
        if (GlobalConfig.blackList.contains(event.getSender().getId())) {
            Objects.requireNonNull(event.getGroup().get(event.getSender().getId())).kick("踢出黑名单用户", true);
        }
    }
}
```

<b>以上被标记的所有方法都会在<code>MessageAnnotationProcessor</code>中通过工厂生产出特定的方法执行类, 并在执行类的<code>
execute()</code>中开起一个线程反射调用</b>

除了以上方式, 你也可以实现<code>PluginRegister</code>接口, 并在实现类上打上<code>@Plugin</code>注解(
这个注解的作用除了继承<code>@Component</code>, 就只有标记作用了), 这时就可以自动执行事件过滤

### 生成指令表:

在方法上加上注解: <code>@Command()</code>自动生成指令表, 通过给机器人发送 "/help" 获取指令表

## 原理:

在processor包下的两个类 <code>MessageAnnotationProcessor</code>和<code>PluginAnnotationProcessor</code>

1. <code>MessageAnnotationProcessor</code>:

   获取所有消息处理方法上的注解, 根据注解的值将Channel进行过滤, 再通过反射调用到匹配到的方法

2. <code>PluginAnnotationProcessor</code>:
   如果你不喜欢用注解来实现的方式, 你可以通过实现<code>PluginRegister</code>接口, 当你实现了<code>register()</code>方法后,
   这个类会先获取所有过滤器执行过滤方法,
   再扫瞄实现了<code>PluginRegister</code>接口的类并为他们注册, 同时为他们传递一个过滤后的<code>Channel</code>

   > 实现过滤的做法是: 将所有把实现了<code>ChannelFilterInter</code>的bean对象取出来,
   执行过滤方法之后再放进去

权限控制/消息匹配/规则过滤 的原理都是通过过滤<code>Channel</code>实现, 在<code>MessageAnnotationProcessor</code>中实现

## 联系方式:

email: 2978086497@qq.com

## 博客:

<a href=https://erzbir.com>erzbir.com</a>
