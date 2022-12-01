package com.erzbir.mirai.numeron.plugins.game.sign;

import com.erzbir.mirai.numeron.enums.FilterRule;
import com.erzbir.mirai.numeron.enums.PermissionType;
import com.erzbir.mirai.numeron.listener.Listener;
import com.erzbir.mirai.numeron.listener.massage.Message;
import com.erzbir.mirai.numeron.plugins.game.sign.entity.User;
import com.erzbir.mirai.numeron.processor.Command;
import com.erzbir.mirai.numeron.store.RedisStore;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.message.data.PlainText;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

/**
 * @author Erzbir
 * @Date: 2022/12/1 10:24
 */
@Listener
@SuppressWarnings("unused")
public class Game {
    private final OkHttpClient client = new OkHttpClient();

    private net.mamoe.mirai.message.data.Message sign(Member member, Group group) throws IOException {
        RedisStore redisStore = RedisStore.getInstance();
        String avatarUrl = member.getAvatarUrl();
        MessageChainBuilder toMsg = new MessageChainBuilder();
        Request request = new Request.Builder().get().url(avatarUrl).build();
        Response response = client.newCall(request).execute();
        InputStream inputStream = Objects.requireNonNull(response.body()).byteStream();
        Image uploadImage = Contact.uploadImage(group, inputStream);
        // 从redis获取个人数据
        User userData = User.fromJson(redisStore.get("user:data:${sender.id}"));
        if (userData == null) {
            userData = new User(member.getId(), 0, 0, 0, 0, 0);
        }
        int addMeiLi = 3;
        int addTiLi = 10;
        int addCoins = new Random().nextInt(100);
        // 累计签到计算
        long userLastSignIn = userData.lastSignIn;
        Calendar userLastSignInDay = new Calendar.Builder().setInstant(userLastSignIn).build();
        String prevSignIn = redisStore.get("user:last:signIn");
        if (prevSignIn == null) {
            prevSignIn = String.valueOf(new Date().getTime());
        }
        Calendar prevSignDay = new Calendar.Builder().setInstant(Long.parseLong(prevSignIn)).build();
        Calendar toDay = Calendar.getInstance();
        // 如果上次签到不是今天,重置签到计数器
        if (!(prevSignDay.get(Calendar.YEAR) == toDay.get(Calendar.YEAR)
                && prevSignDay.get(Calendar.DAY_OF_YEAR) == toDay.get(Calendar.DAY_OF_YEAR))) {
            redisStore.set("user:signIn:rank", "0", -1L);
        }
        if (userLastSignInDay.get(Calendar.YEAR) == toDay.get(Calendar.YEAR)
                && userLastSignInDay.get(Calendar.DAY_OF_YEAR) == toDay.get(Calendar.DAY_OF_YEAR) - 1) {
            userData.alreadySignInDays += 1;
            userData.lastSignIn = new Date().getTime();
        } else if (userLastSignInDay.get(Calendar.YEAR) == toDay.get(Calendar.YEAR)
                && userLastSignInDay.get(Calendar.DAY_OF_YEAR) == toDay.get(Calendar.DAY_OF_YEAR)) {
            toMsg.append(new At(member.getId()));
            toMsg.append(new PlainText(" 你今天已经签到过了哦"));
            return toMsg.build();
        } else {
            userData.alreadySignInDays = 1;
            userData.lastSignIn = new Date().getTime();
        }
        if (redisStore.get("user:signIn:rank") == null) {
            redisStore.set("user:signIn:rank", "0", -1L);
        }
        int rank = Integer.parseInt(redisStore.get("user:signIn:rank"));
        rank += 1;
        redisStore.set("user:signIn:rank", "$rank", -1L);
        redisStore.set("user:last:signIn", String.valueOf(new Date().getTime()), -1L);
        userData.tiLi += addTiLi;
        userData.meiLi += addMeiLi;
        userData.coins += addCoins;
        redisStore.set("user:data:${sender.id}", User.toJson(userData), -1L);
        toMsg.append(Image.Builder.newBuilder(uploadImage.getImageId()).build());
        toMsg.append(new PlainText("昵称: " + member.getNick() + "\n"));
        toMsg.append(new PlainText("账号: " + member.getId() + "\n"));
        toMsg.append(new PlainText("排行: 第 " + rank + " 位签到\n"));
        toMsg.append(new PlainText("累签: " + userData.alreadySignInDays + " 天\n"));
        toMsg.append(new PlainText("魅力: " + userData.meiLi + "\n"));
        toMsg.append(new PlainText("体力: " + userData.tiLi + "\n"));
        toMsg.append(new PlainText("金币: " + addCoins + "\n"));
        toMsg.append(new PlainText("金币余额: " + userData.coins));
        return toMsg.build();
    }

    @Command(name = "签到", dec = "签到", help = "发送 \"签到\" 即可")
    @Message(text = "签到", filterRule = FilterRule.BLACK, permission = PermissionType.ALL)
    public void sign(GroupMessageEvent event) throws IOException {
        event.getSubject().sendMessage(sign(event.getSender(), event.getGroup()));
    }
}
