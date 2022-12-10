# 项目介绍:

## 这是什么:

这是一个QQ机器人, 可以看作是一个机器人客服.
最底层的逻辑由net.mamoe.mirai提供

- net.mamoe.mirai:
  > 这是一个封装了QQ各种api和协议的一个框架,
  > 包含QQ的所有基本功能, 也就是可以通过这个框架 发消息/发公告/加好友等等QQ的相关操作

>

## 项目结构:

所有代码均在 Numeron/src/main/java/com/erzbir/mirai/numeron/ 下
结构如下:

- [configs](src/main/java/com/erzbir/mirai/numeron/configs)
  > 基础数据库数据/基础配置文件 解析到这个包的类中
- [enums](src/main/java/com/erzbir/mirai/numeron/enums)
  > 定义的枚举
- [filter](src/main/java/com/erzbir/mirai/numeron/filter)
  > 监听事件过滤器, 分别是 [消息过滤](src/main/java/com/erzbir/mirai/numeron/filter/message)/
  > [权限过滤](src/main/java/com/erzbir/mirai/numeron/filter/permission)/[规则过滤](src/main/java/com/erzbir/mirai/numeron/filter/rule)
- [handler](src/main/java/com/erzbir/mirai/numeron/handler)
  > 监听到不同事件后的不同处理方法
- [job](src/main/java/com/erzbir/mirai/numeron/job)
  > 定时器任务
- [listener](src/main/java/com/erzbir/mirai/numeron/listener)
  > 这里暂时只放了注解, 被这个包下的注解标注的方法将在监听到符合注解中的规定的事件后执行
- [plugins](src/main/java/com/erzbir/mirai/numeron/plugins)
  > 所有实际与QQ相关的功能
- [processor](src/main/java/com/erzbir/mirai/numeron/processor)
  > 注解处理器, 对标注了注解的方法和类等进行处理
- [store](src/main/java/com/erzbir/mirai/numeron/store)
  > 数据库相关
- [utils](src/main/java/com/erzbir/mirai/numeron/utils)
  > 一些工具类

其他细节看[README](README.md)
