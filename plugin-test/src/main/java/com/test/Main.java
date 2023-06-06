package com.test;

import com.erzbir.numeron.console.plugin.NumeronDescription;
import com.erzbir.numeron.console.plugin.NumeronPlugin;

/**
 * @author Erzbir
 * @Date: 2023/4/29 22:07
 */// 按两次 ⇧ 打开“随处搜索”对话框并输入 `show whitespaces`，
// 然后按 Enter 键。现在，您可以在代码中看到空格字符。
public class Main extends NumeronPlugin {
    public static Main INSTANCE = new Main();

    public Main() {
        super(new NumeronDescription.Builder("com.test.numeron", "1.0").name("测试").desc("只是测试").author("Erzbir").build());
    }

    @Override
    public void onEnable() {
        System.out.println("asasasaomafofnwofnawoifnaiof");
    }

    @Override
    public void onDisable() {

    }

    @Override
    public void onLoad() {

    }

    @Override
    public void onUnLoad() {

    }
}