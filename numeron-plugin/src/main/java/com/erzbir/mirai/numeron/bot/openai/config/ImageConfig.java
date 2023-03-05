package com.erzbir.mirai.numeron.bot.openai.config;

import com.erzbir.mirai.numeron.bot.openai.JsonUtil;
import com.theokanning.openai.image.CreateImageRequest;

import java.io.File;
import java.io.Serializable;

/**
 * @author Erzbir
 * @Date: 2023/3/3 23:53
 */
public class ImageConfig implements Serializable {
    private int n = 1;
    private String size = ImageSize.LARGE;
    private String format = "b64_json";
    private String folder = "";
    private boolean save = false;


    public ImageConfig() {

    }

    public ImageConfig(int n, String size, String format, String folder, boolean save) {
        this.n = n;
        this.size = size;
        this.format = format;
        this.folder = folder;
        this.save = save;
    }

    public static ImageConfig getInstance() {
        return JsonUtil.load("erzbirnumeron/plugin-configs/chatgpt/image.json", ImageConfig.class);
        //return new ImageConfig();
    }

    public CreateImageRequest load() {
        File file = new File(folder);
        if (!file.exists()) {
            file.mkdirs();
        }
        return CreateImageRequest.builder().n(n)
                .size(String.valueOf(size))
                .responseFormat(format)
                .prompt("")
                .build();
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public boolean isSave() {
        return save;
    }

    public void setSave(boolean save) {
        this.save = save;
    }
}
