package com.erzbir.mirai.numeron.plugins.qqmanage.action;

import com.erzbir.mirai.numeron.config.GlobalConfig;
import com.erzbir.mirai.numeron.listener.other.MemberJoin;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.NormalMember;
import net.mamoe.mirai.event.events.MemberJoinEvent;
import net.mamoe.mirai.message.data.At;

import java.io.IOException;
import java.net.URL;

/**
 * @author Erzbir
 * @Date: 2022/11/27 13:08
 */
public class JoinAction {


    public void welcome(MemberJoinEvent event) {
        @MemberJoin NormalMember member = event.getMember();
        if (!GlobalConfig.blackList.contains(member.getId())) {
            try {

                member.sendMessage(new At(member.getId())
                        .plus(Contact.uploadImage(member, new URL(member.getAvatarUrl())
                                .openConnection().getInputStream())).plus("欢迎入群"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
